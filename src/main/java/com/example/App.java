package com.example;


import java.io.File;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;

import com.example.Database.DBClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args) {
        // recupero il percorso del file jar che potrei passare come argomento
        String jarFilePath = "/home/francesco/Scrivania/test/uml/jaranalyzer/src/main/resources/spigot-api-1.20.1-R0.1-20230626.214335-16.jar"; // Percorso del file JAR di Spigot API
        // Informazioni di connessione al database MySQL
        String username = "francesco";
        String password = "password";
        String database = "Plugins";

        DBClass db = new DBClass(username, password, database);
        db.startConnection();
        db.startMigrations();
        




        File file = CAnalyzer.checkFile(jarFilePath);
        List<Class> classes = new LinkedList<>();
        classes = CAnalyzer.loadClasses(file);
        System.out.println(classes.size());
        for(Class c : classes) {
            //CAnalyzer.analizeClass(c);
        }



        db.stopConnection();
    }

}
