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
        String jarFilePath = "/home/francesco/Scrivania/test/uml/jarToUml/myproject/src/main/resources/spigot-api-1.20.1-R0.1-20230626.214335-16.jar"; // Percorso del file JAR di Spigot API
        
        File file = Main.checkFile(jarFilePath);
        List<Class> classes = new LinkedList<>();
        classes = Main.loadClasses(file);
        System.out.println(classes.size());
        for(Class c : classes) {
            Main.analizeClass(c);
        }

        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();
            // recupero tutte le entries del jar
            /*while(entries.hasMoreElements()) {
                // se l'elemento è una classe recuperala
                JarEntry entry = entries.nextElement();
                if(!entry.getName().contains(".class")) continue;
                Class c = entry.getClass();
                System.out.println(c.getSimpleName());

            }*/


            //System.out.println(entries.toString());
            /*while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.isDirectory()) {
                    String packageName = entry.getName().replace("/", ".");
                    //packageNames.add(packageName);
                } else if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace("/", ".").replace(".class", "");
                    int packageIndex = className.lastIndexOf('.');
                    if (packageIndex != -1) {
                        String packageName = className.substring(0, packageIndex);
                        packageNames.add(packageName);
                    }
                }
            }
*/
            jarFile.close();
            /*for(String name : packageNames) {
                System.out.println(name);
            }*/

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        //System.out.println("START");

        //for (String packageName : packageNames) {
            //System.out.println(packageName);
            //if (packageName.equals("org.bukkit.block")) {
                //searchInPackage(jarFilePath, packageName);
            //}
        //}


        // recupera la classe


        //System.out.println("END");
        
    }
    private static Set<String> getPackageNamesFromJar(String jarFilePath) throws IOException {
        Set<String> packageNames = new HashSet<>();
        JarFile jarFile = new JarFile(jarFilePath);

        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.isDirectory()) {
                String packageName = entry.getName().replace("/", ".");
                //packageNames.add(packageName);
            } else if (entry.getName().endsWith(".class")) {
                String className = entry.getName().replace("/", ".").replace(".class", "");
                int packageIndex = className.lastIndexOf('.');
                if (packageIndex != -1) {
                    String packageName = className.substring(0, packageIndex);
                    packageNames.add(packageName);
                }
            }
        }

        jarFile.close();
        return packageNames;
    }

   /*private static void searchInPackage(String jarFilePath, String packageName) throws IOException {
        JarFile jarFile = new JarFile(jarFilePath);

        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (!entry.isDirectory() && entry.getName().startsWith(packageName.replace(".", "/") + "/")) {
                String className = entry.getName().replace("/", ".").replace(".class", "");

                try {
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    Class<?> clazz = classLoader.loadClass(className);

                    
                    if (clazz.getSimpleName().equals("Chest")) {
                        System.out.println("  - " + className);

                        /*Field[] fields = clazz.getDeclaredFields();
                        System.out.println("    - Attributes:");
                        for (Field field : fields) {
                            System.out.println("      - " + field.getName());
                        }*/

                        /*Method[] methods = clazz.getDeclaredMethods();
                        System.out.println("    - Methods:");
                        for (Method method : methods) {
                            System.out.println("      - " + method.getName());
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        jarFile.close();
    }*/

    private static void searchInPackage(String jarFilePath, String packageName) throws IOException {
        File jarFile = new File(jarFilePath);
        URL jarUrl = jarFile.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});

        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                String className = entry.getName().replace("/", ".").replace(".class", "");

                try {
                    Class<?> clazz = classLoader.loadClass(className);

                    if (clazz.getSimpleName().equals("Chest")) {
                        System.out.println("  - " + className);

                        
                      

                        Field[] fields2 = clazz.getFields();
                        System.out.println("    - Attributes2:");
                        for (Field field : fields2) {
                            System.out.println("      - " + field.getName());
                        }

                        

                        Method[] methods2 = clazz.getMethods();
                        System.out.println("    - Methods:");
                        for (Method method : methods2) {
                            System.out.println("      - " + method.getName());
                        }


                        

                    
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        jar.close();
        classLoader.close();
    }


}
