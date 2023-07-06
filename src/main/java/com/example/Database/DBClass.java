package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Random;

public class DBClass {

    private static Connection conn;
    String url = "jdbc:mysql://localhost:3306/";
    String username; String password; String database;

    public DBClass(String username, String password, String database) {
        //url = "jdbc:mysql://localhost:3306/";
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public boolean startConnection() {
        try {
            conn = DriverManager.getConnection(url + database, username, password);
            System.out.println("Connessione al database riuscita!");
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Connessione al database non riuscita!");
            e.printStackTrace();
        }
        return false;
    }

  public void stopConnection() {
    System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
    try {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Connessione al database chiusa correttamente.");
        }
    } catch (SQLException e) {
        System.err.println("Errore durante la chiusura della connessione al database: " + e.getMessage());
    }
}


    public boolean startMigrations() {
        // Creazione di uno statement
        Statement statement;
        try {
            statement = conn.createStatement();
            // Esecuzione della query per ottenere i nomi di tutte le tabelle nel database
            String showTablesQuery = "SHOW TABLES";
            statement.execute(showTablesQuery);
            ResultSet resultSet = statement.getResultSet();

            // Eliminazione di tutte le tabelle
            LinkedList<String> tables = new LinkedList<>();
            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                tables.add(tableName);
                
            }

            while (tables.size() > 0) {
                Random random = new Random();
                int i = random.nextInt(tables.size());
                String tableName = tables.get(i);
                try {
                    String dropTableQuery = "DROP TABLE " + tableName;
                    statement.executeUpdate(dropTableQuery);
                    System.out.println("Tabella eliminata: " + tableName);
                    tables.remove(i);
                } catch(SQLIntegrityConstraintViolationException e) {
                    continue;
                }
            }

            System.out.println("Tutte le tabelle sono state eliminate.");

            // Creazione di uno statement
            statement = conn.createStatement();

            // Query di creazione della tabella
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Classi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nome VARCHAR(255),"
                    + "percorso VARCHAR(255),"
                    + "colonna3 DECIMAL(10,2)"
                    + ")";

            // Esecuzione della query di creazione della tabella
            statement.executeUpdate(createTableQuery);
            System.out.println("Tabella Classi creata con successo.");

            // Creazione di uno statement
            statement = conn.createStatement();

            // Query di creazione della tabella
            createTableQuery = "CREATE TABLE IF NOT EXISTS Attributi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nome VARCHAR(255),"
                    + "Classe INT,"
                    + "CONSTRAINT FK_AttributoClasseOrder FOREIGN KEY (Classe)\n"
                    + "    REFERENCES Classi(id)"
                    + ")";

            // Esecuzione della query di creazione della tabella
            statement.executeUpdate(createTableQuery);
            System.out.println("Tabella Attributi creata con successo.");

            // Creazione di uno statement
            statement = conn.createStatement();

            // Query di creazione della tabella
            createTableQuery = "CREATE TABLE IF NOT EXISTS Metodi ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nome VARCHAR(255),"
                    + "Classe INT,"
                    + "CONSTRAINT FK_MetodoClasseOrder FOREIGN KEY (Classe)\n"
                    + "    REFERENCES Classi(id)"
                    + ")";

            // Esecuzione della query di creazione della tabella
            statement.executeUpdate(createTableQuery);
            System.out.println("Tabella Metodi creata con successo.");
        return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

}

/*
 * 
 * 
 * conn.setAutoCommit(false);

            // Query di inserimento
            String insertQuery = "INSERT INTO nome_tabella (nome, eta, email) VALUES (?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
                // Dati da inserire
                String[] nomi = { "Mario", "Luigi", "Peach" };
                int[] età = { 30, 32, 28 };
                String[] emails = { "mario@example.com", "luigi@example.com", "peach@example.com" };

                // Aggiungi i parametri al batch
                for (int i = 0; i < nomi.length; i++) {
                    statement.setString(1, nomi[i]);
                    statement.setInt(2, età[i]);
                    statement.setString(3, emails[i]);
                    statement.addBatch();
                }

                // Esegui il batch di inserimenti
                int[] righeInserite = statement.executeBatch();

                // Esegui il commit esplicito
                conn.commit();

                System.out.println("Inserimenti completati con successo. Righe inserite: " + righeInserite.length);

                
 */
