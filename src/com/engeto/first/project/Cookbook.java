package com.engeto.first.project;

import java.util.ArrayList;

public class Cookbook /*zásobník jídel*/ extends ArrayList<Dish> {

    @Override
    public String toString() {
        String returnString = "";
        int i = 1;
        // Vypsání každého pokrmu v zásobníku jídel
        for (Dish dish : this) {
            returnString += ("\n" +
                    i +
                    ". " +
                    dish.getTitle() +
                    " = Cena: " +
                    dish.getPrice() + " Kč," +
                    Settings.DELIMITER + "Čas přípravy: " +
                    dish.getPreparationTimeInMinutes() + " min," +
                    Settings.DELIMITER + "Fotografie: " +
                    dish.getImage() + "," +
                    Settings.DELIMITER + "Kategorie: " +
                    dish.getCategory().getDescription());
            i++;
        }
        return returnString;
    }

}
