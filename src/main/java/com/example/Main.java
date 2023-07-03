package com.example;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {

    public static File checkFile(String path) {
        // controllo se il file esiste
        File f = new File(path);
        if(!f.exists()) { System.err.println("jar non trovato"); System.exit(-1); }

        return f;
    }

    public static List<Class> loadClasses(File file) {
        List<Class> classes = new LinkedList<>();
        try {
            JarFile jf = new JarFile(file);
            URL jarUrl = file.toURI().toURL();

            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
            
            Enumeration<JarEntry> enumeration = jf.entries();
            while(enumeration.hasMoreElements()) {
                JarEntry je = enumeration.nextElement();
                //System.out.println(je);
                if(je.isDirectory()) continue;
                if(!je.getName().endsWith(".class")) continue;
                //!entry.isDirectory() && entry.getName().endsWith(".class")
                
                String className = je.getName().replace("/", ".").replace(".class", "");
                try {
                    //errore quì quando piglia la classe di snakeyaml, io vorrei che la passasse se trova un errore e va avanti
                    Class<?> c = classLoader.loadClass(className);
                    classes.add(c);
                } catch (ClassNotFoundException e) {
                    continue;
                    // TODO Auto-generated catch block
                    ///System.out.println(classes.size());
                    //e.printStackTrace();
                    //System.out.println(className);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return classes;
    }
 
    public static void analizeClass(Class c) {

        System.out.println("  - " + c.getSimpleName());

        Field[] fields = c.getDeclaredFields();
        System.out.println("    - Attributes:");
        for (Field field : fields) {
            System.out.println("      - " + field.getName());
        }

        Method[] methods = c.getDeclaredMethods();
        System.out.println("    - Methods:");
        for (Method method : methods) {
            System.out.println("      - " + method.getName());
        }
    }
}
