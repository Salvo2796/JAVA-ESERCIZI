package model;

import java.sql.*;
import java.util.ArrayList;

import exceptions.Ruolo;

public class RepositoryPersona {

    public boolean insertPersona(Persona p) {

        /*ottiene una connessione attiva al database, metodo statico Connessione.getConnection(), che molto  gestisce il collegamento JDBC a MySQL.*/
        Connection conn = Connessione.getConnection();

        /*dichiara un oggetto PreparedStatement, che verrà usato per eseguire una query parametrica (con ?) sul DB.*/
        PreparedStatement ps = null;
        /*dichiara una variabile per contenere i risultati di una query*/
        ResultSet rs = null;

        String sqlPersona = "INSERT INTO persone(nome,cognome,birth_date,cf) VALUES(?,?,?,?)";

        try {
            conn.setAutoCommit(false); // disattivo l'autocommit
            /*"Esegui questa query(sqlPersona) e restituiscimi le chiavi primarie generate automaticamente."*/
            ps = conn.prepareStatement(sqlPersona, PreparedStatement.RETURN_GENERATED_KEYS);

            /*ps è il PreparedStatement già creato con la query.
             .setString(...) dice: "voglio inserire una stringa" poichè getNome ci da una Stringa.
             1 indica il primo punto interrogativo nella query SQL (le posizioni partono da 1, non da 0).
             p.getNome() ottiene il valore del campo nome dell'oggetto Persona p.*/
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setDate(3, Date.valueOf(p.getBirthDate()));
            ps.setString(4, p.getCf());

            ps.executeUpdate();// Eseguiamo l'inserimento nella tabella 'persone'
            rs = ps.getGeneratedKeys(); // crea una "tabella temporanea"(ResultSet) con le chiavi generate

            if (rs.next()) {/*.next() sposta il cursore sulla prima (e in questo caso unica) riga.
                              Se c'è almeno una chiave generata, entra nell'if.*/

                int idPersona = rs.getInt(1);/*Legge il primo valore della riga (cioè la chiave generata).
                                                         1 indica la prima colonna del ResultSet, che in questo caso è l'id appena creato.*/

                p.setId(idPersona);/*Assegna l'ID generato all'oggetto Persona in memoria*/

                if (!(p instanceof Manager || p instanceof Dipendente)) {
                    // se Persona p è una semplice persona committo
                    conn.commit();
                    return true;
                }

                // Se p è Dipendente (o Manager)
                if (p instanceof Dipendente d) {

                    String ruolo = null;
                    if (d instanceof Manager m) {
                        ruolo = String.valueOf(m.getRuolo());
                    }

                    String sqlDip = "INSERT INTO dipendenti(stipendio,data_di_assunzione,ruolo,fk_id_persona) Values (?,?,?,?)";

                    try {
                        PreparedStatement psDip = conn.prepareStatement(sqlDip);
                        psDip.setDouble(1, d.getStipendio());
                        psDip.setDate(2, Date.valueOf(d.getDataAssunzione()));
                        if (ruolo != null) {
                            psDip.setString(3, ruolo);
                        } else {
                            psDip.setString(3, null);
                        }
                        psDip.setInt(4, idPersona);

                        psDip.executeUpdate();
                        conn.commit();// se va bene l'inserimento di dipendente o manager committo
                        return true;

                    } catch (Exception e) {
                        e.printStackTrace();
                        conn.rollback();// se l'inserimento del dipendente o manager fallisce, annullo la porzione di codice sopra
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return false;
    }

    public boolean checkCf(String cf) {
        Connection conn = Connessione.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlPersona = "SELECT * FROM persone WHERE cf = ?";

        try {
            ps = conn.prepareStatement(sqlPersona);
            ps.setString(1, cf);
            rs = ps.executeQuery();

            if (rs.next()) {

                return false;  // CF NON valido perché già esiste
            }

            return true; // CF valido, non presente nel DB

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        }
    }


    public ArrayList<Persona> getPersone() {
        ArrayList<Persona> array = new ArrayList<>();
        Connection conn = Connessione.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // LEFT JOIN per prendere anche persone senza dati da dipendenti
        String sql = "SELECT * FROM persone LEFT JOIN dipendenti ON persone.id = dipendenti.fk_id_persona ";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona p;
                String ruolo = rs.getString("ruolo"); // salvo il valore del ruolo
                double stipendio = rs.getDouble("stipendio"); // salvo il valore dello stipendio

                if (ruolo != null) { //se ha un ruolo è Manager
                    Manager m = new Manager();
                    m.setRuolo(Ruolo.valueOf(ruolo));
                    m.setStipendio(stipendio);
                    m.setDataAssunzione((rs.getDate("data_di_assunzione")).toLocalDate());
                    p = m;
                } else if (stipendio != 0) { //se non ha un ruolo ma ha uno stipendio, è un dipendente
                    Dipendente d = new Dipendente();
                    d.setStipendio(stipendio);
                    d.setDataAssunzione((rs.getDate("data_di_assunzione")).toLocalDate());
                    p = d;
                } else {
                    // Persona semplice
                    p = new Persona();
                }
                // Campi in comune
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCognome(rs.getString("cognome"));
                p.setBirthDate((rs.getDate("birth_date")).toLocalDate());
                p.setCf(rs.getString("cf"));

                array.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }

    public ArrayList<Persona> getPersonaByStipendio(double salary) {
        ArrayList<Persona> array = new ArrayList<>();
        Connection conn = Connessione.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // INNER JOIN per prendere solo persone con dati dipendente (quindi stipendio presente)
        String sql = "SELECT * FROM persone INNER JOIN dipendenti ON persone.id = dipendenti.fk_id_persona WHERE dipendenti.stipendio >= ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, salary);
            rs = ps.executeQuery();

            while (rs.next()) {
                Dipendente d;
                String ruolo = rs.getString("ruolo"); // salvo il valore del ruolo

                if (ruolo != null) { //se ha un ruolo è Manager
                    Manager m = new Manager();
                    m.setRuolo(Ruolo.valueOf(ruolo));
                    m.setStipendio(rs.getDouble("stipendio"));
                    m.setDataAssunzione((rs.getDate("data_di_assunzione")).toLocalDate());
                    d = m;
                } else {
                    d = new Dipendente();
                    d.setStipendio(rs.getDouble("stipendio"));
                    d.setDataAssunzione((rs.getDate("data_di_assunzione")).toLocalDate());
                }
                // Campi in comune
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setCognome(rs.getString("cognome"));
                d.setBirthDate((rs.getDate("birth_date")).toLocalDate());
                d.setCf(rs.getString("cf"));

                array.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public Persona getPersonaByCf(String cf) {
        Persona p = null;
        Connection conn = Connessione.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //LEFT JOIN per prendere anche le persone che non sono dipendenti
        String sql = "SELECT * FROM persone LEFT JOIN dipendenti ON persone.id = dipendenti.fk_id_persona where cf=?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cf);
            rs = ps.executeQuery();

            if (rs.next()) {
                String ruolo = rs.getString("ruolo");
                double stipendio = rs.getDouble("stipendio");

                if (ruolo != null) { //se ha un ruolo è Manager
                    Manager m = new Manager();
                    m.setRuolo(Ruolo.valueOf(ruolo));
                    m.setStipendio(stipendio);
                    m.setDataAssunzione((rs.getDate("data_di_assunzione")).toLocalDate());
                    p = m;
                } else if (stipendio != 0) { //se non ha un ruolo ma ha uno stipendio, è un dipendente
                    Dipendente d = new Dipendente();
                    d.setStipendio(stipendio);
                    d.setDataAssunzione((rs.getDate("data_di_assunzione")).toLocalDate());
                    p = d;
                } else {
                    // Persona semplice
                    p = new Persona();
                }
                // Campi in comune
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCognome(rs.getString("cognome"));
                p.setBirthDate((rs.getDate("birth_date")).toLocalDate());
                p.setCf(rs.getString("cf"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public boolean eliminaPersona(String cf) {
        Connection conn = Connessione.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM persone WHERE cf = ?"; //impostato il CASCADE nelle chiave esterna

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, cf);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updatePersona(Persona p, String cf) {
        Connection conn = Connessione.getConnection();
        PreparedStatement psDipendente = null;
        PreparedStatement psPersone = null;

        int numDipendenti = 0;
        int numPersone = 0;
        int numDef=0;

        String sqlUpdateDipendente = "UPDATE dipendenti d JOIN persone p ON d.fk_id_persona=p.id SET d.stipendio = ?, d.data_di_assunzione = ?, d.ruolo = ? WHERE p.cf = ?";
        String sqlUpdatePersone = "UPDATE persone SET nome = ?, cognome = ?, birth_date = ?, cf = ? WHERE cf = ?";

        try {

            if (p instanceof Dipendente d) { // se p è un dipendente assegno ai campi della tabella dipendenti i valori, recuperandoli dall'oggetto p che abbiamo ricevuto

                psDipendente = conn.prepareStatement(sqlUpdateDipendente);

                psDipendente.setDouble(1, d.getStipendio());
                psDipendente.setDate(2, Date.valueOf(d.getDataAssunzione()));

                if (p instanceof Manager m) { // se è un Manager assegno il valore a ruolo, altrimenti metto null
                    psDipendente.setString(3, m.getRuolo().toString());
                } else {
                    psDipendente.setString(3, null);
                }

                psDipendente.setString(4, cf); // usiamo il cf che abbiamo ricevuto per identificare la persona
                numDipendenti=psDipendente.executeUpdate();

            }
            // Ora aggiorna la tabella 'persone', sono i campi in comune
            psPersone = conn.prepareStatement(sqlUpdatePersone);

            psPersone.setString(1, p.getNome());
            psPersone.setString(2, p.getCognome());
            psPersone.setDate(3, Date.valueOf(p.getBirthDate()));
            psPersone.setString(4, p.getCf());
            psPersone.setString(5, cf); //uso il cf ricevuto
            numPersone=psPersone.executeUpdate();

            if (numDipendenti >0 && numPersone>0)
                 numDef = 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return numDef;
    }

    public int PromozioneDipendente(Persona p) {
        Connection conn = Connessione.getConnection();
        PreparedStatement psUpdate = null;
        PreparedStatement psInsert = null;
        int num = 0;

        String sqlUpdate = "UPDATE dipendenti SET stipendio = ?, data_di_assunzione = ?, ruolo = ? WHERE fk_id_persona = ?";
        String sqlInsert = "INSERT INTO dipendenti (stipendio, data_di_assunzione, ruolo, fk_id_persona) VALUES (?, ?, ?, ?)";

        try {
            Dipendente d;

            if (p instanceof Dipendente) {
                d = (Dipendente) p;
            } else {
                d = new Dipendente();
                d.setId(p.getId());
                d.setNome(p.getNome());
                d.setCognome(p.getCognome());
                d.setCf(p.getCf());
                d.setBirthDate(p.getBirthDate());
            }

            psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setDouble(1, d.getStipendio());
            psUpdate.setDate(2, Date.valueOf(d.getDataAssunzione()));
            if (d instanceof Manager m) {
                psUpdate.setString(3, m.getRuolo().toString());
            } else {
                psUpdate.setString(3, null);
            }
            psUpdate.setInt(4, d.getId());

            num = psUpdate.executeUpdate();

            // Se non ha aggiornato nessuna riga, faccio INSERT
            if (num == 0) {
                psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setDouble(1, d.getStipendio());
                psInsert.setDate(2, Date.valueOf(d.getDataAssunzione()));
                if (d instanceof Manager m) {
                    psInsert.setString(3, m.getRuolo().toString());
                } else {
                    psInsert.setString(3, null);
                }
                psInsert.setInt(4, d.getId());

                num = psInsert.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }


}