package com.engeto.first.project;
import java.io.File;


public class Settings {
    public static String cookbookFileName() {
        return "zasobnik_jidla.txt";
    }

    public static String menuFileName() {
        return "aktualni_menu.txt";
    }

    public static String firstOrdersFileName() {return "seznam_objednavek-prvni-cast.txt";}
    public static String secondOrdersFileName() {return "seznam_objednavek-druha-cast.txt";}

    public static String fileToBeRead() {return "test-objednavek-k-nacteni.txt";}

    public static String delimiter()   {
        return "\t";
    }

    public static String textSeparator() {
        return "\n=======================\n";
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
           return true;
        }
        return false;
    }
}
