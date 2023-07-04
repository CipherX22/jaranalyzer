package com.example;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Server;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args) {
        // recupero il percorso del file jar che potrei passare come argomento
        String jarFilePath = "/home/francesco/Scrivania/test/uml/jaranalyzer/src/main/resources/spigot-api-1.20.1-R0.1-20230626.214335-16.jar"; // Percorso del file JAR di Spigot API
        
        File file = Classes.checkFile(jarFilePath);
        List<Class> classes = new LinkedList<>();
        classes = Classes.loadClasses(file);
        System.out.println(classes.size());
        for(Class c : classes) {
            Classes.analizeClass(c);
        }



    }

}
