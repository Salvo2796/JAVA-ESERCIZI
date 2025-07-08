package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Collection;

public class RepositoryPersona {
    private HashMap<Integer, Persona> map = new HashMap<>();
    private static int counter = 0;

    public RepositoryPersona() {
    }

    private static int generateKey() {
        return ++counter;
    }

    public int insertPersona(Persona p) {
        int num = 0;
        Connection conn = Connessione.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlPersona = "INSERT INTO persone(nome, cognome, birth_date, cf) VALUES (?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getCognome());
            ps.setDate(3, java.sql.Date.valueOf(p.getBirthDate()));
            ps.setString(4, p.getCf());

            num = ps.executeUpdate();

            // Recupero ID generato
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idPersona = rs.getInt(1);
                p.setId(idPersona); // se hai il metodo setId

                // Se p è Dipendente (o Manager)
                if (p instanceof Dipendente) {
                    Dipendente d = (Dipendente) p;

                    String ruolo = null;
                    if (d instanceof Manager) {
                        ruolo = "Manager";
                    }

                    String sqlDip = "INSERT INTO dipendenti(stipendio, data_di_assunzione, ruolo, fk_id_persona) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement psDip = conn.prepareStatement(sqlDip)) {
                        psDip.setDouble(1, d.getStipendio());
                        psDip.setDate(2, java.sql.Date.valueOf(d.getDataDiAssunzione()));
                        if (ruolo != null) {
                            psDip.setString(3, ruolo);
                        } else {
                            psDip.setNull(3, java.sql.Types.VARCHAR);
                        }
                        psDip.setInt(4, idPersona);

                        psDip.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return num;
    }

    public Persona getPersona(int id) {
        return this.map.get(id);
    }

    public Persona getPersonaByCf(String cf) {
        for (Persona p : this.map.values()) {
            if (p.getCf().equalsIgnoreCase(cf)) {
                return p;
            }
        }
        return null;
    }

    public Persona getPersonaById(Integer id){
        return this.map.get(id);
    }

    public boolean eliminaPersona(int id) {
        return this.map.remove(id) != null;
    }

    public boolean checkCf(String cf) {
        for (Persona p : this.map.values()) {
            if (p.getCf().equalsIgnoreCase(cf)) {
                return true;
            }
        }
        return false;
    }

    public Collection<Persona> selectAll() {
        return this.map.values();
    }

    public void updatePersona(int id, Persona personaModificata) {
        personaModificata.setId(id);
        this.map.put(id, personaModificata);
    }

    public boolean HashMapEmpty(){
        return this.map.isEmpty();
    }

}
