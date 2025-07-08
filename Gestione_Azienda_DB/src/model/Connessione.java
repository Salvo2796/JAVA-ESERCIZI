package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connessione {

    static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    static final String DB_URL="jdbc:mysql://localhost:3306/gestione_azienda";
    static final String USER="root";
    static final String PASSWORD="root";

    private static Connection conn=null;


    static
    {
        //Carico i driver necessari
        try {
            Class.forName(JDBC_DRIVER);
            conn=DriverManager.getConnection(DB_URL,USER,PASSWORD);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }catch (SQLException e) {

            e.printStackTrace();
        }

    }


    public static Connection getConnection()
    {
        return conn;
    }



    private Connessione()
    {

    }



}
