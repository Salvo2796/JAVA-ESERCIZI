package model;

import java.util.HashMap;

import exceptions.ExistingCfException;

public class RepositoryPersona {

    //private ArrayList<Persona> array=new ArrayList<Persona>();

    //LinkedList<Persona> array=new LinkedLst<Persona>();

    /*private HashSet<Persona> array = new HashSet<Persona>(); gestisce elementi unici, non duplicati
    set.add()
    set.remove()*/

    private HashMap<Integer,Persona> map = new HashMap<Integer,Persona>();
    //map.put(1,p) //inserimento o modifica
    //map.remove(p)

   /*for(Persona persona : map.values()) { // questo per recuperare i valori

    }

    for(Map.Entry<Integer, Persona>) entry : map.entrySet()) { // questo per recuperare anche la chiave associata
    Integer Key = entry.getKey();
    Persona val = entry.getValue();
    }*/

    //CRUD

    public void insertPersona(Persona p) {
        p.setId();
        map.put(p.getId(), p);
    }

    public boolean checkCf(String cf)
    {
        boolean flag=true;

        try {

            for (Persona p : map.values())
                if(p.getCf().equalsIgnoreCase(cf))
                    throw new ExistingCfException("Codice Fiscale già presente!"); // lancia l'eccezione personalizzata!

            flag=false;
            return flag;

        } catch (ExistingCfException e) {
            //e.getMessage());
            e.printStackTrace();
            return flag;
        }

    }

    public Persona checkPersonaId(int key) {
        return map.get(key);
    }

    public HashMap<Integer, Persona> selectAll() {
        return map;
    }
    public boolean eliminaPersona(Persona p)
    {
        if(map.containsKey(p.getId())) {
            map.remove(p.getId());
            return true;
        }
            return false;
    }

    public Persona getPersona(String cf)
    {
        for (Persona p : map.values())
            if(p.getCf().equalsIgnoreCase(cf))
                return p;


        return null;
    }

    public void updatePersona(Persona personaDaModificare, Persona personaModificata)
    {
        map.replace(personaDaModificare.getId(), personaModificata);
    }
}