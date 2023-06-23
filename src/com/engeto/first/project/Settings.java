package com.engeto.first.project;

public class Settings {
    public static String cookbookFileName() {
        return "zasobnik_jidla.txt";
    }

    public static String menuFileName() {
        return "aktualni_menu.txt";
    }

    public static String ordersFileName() {return "seznam_objednavek.txt";}

    public static String delimiter()   {
        return "\t";
    }

    public static String textSeparator() {
        return "\n=======================\n";
    }
}
