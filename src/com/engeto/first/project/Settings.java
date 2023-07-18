package com.engeto.first.project;
import java.io.File;

public class Settings {

    public static final String COOKBOOK_FILENAME = "zasobnik_jidla.txt";
    public static final String MENU_FILE_NAME = "aktualni_menu.txt";
    public static final String FIRST_ORDERS_FILENAME = "seznam_objednavek-prvni-cast.txt";
    public static final String SECOND_ORDERS_FILENAME = "seznam_objednavek-druha-cast.txt";
    public static final String FILE_TO_BE_READ = "test-objednavek-k-nacteni.txt";
    public static final String DELIMITER = "\t";
    public static final String TEXT_SEPARATOR = "\n=======================\n";

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
           return true;
        }
        return false;
    }
}
