package com.engeto.first.project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Cookbook /*zásobník jídel*/ extends ArrayList<Dish> {
    // Metody
    // Kuchaři mají možnost některá jídla ze zásobníku vyřadit, přidat, nebo upravit.
    // Má také jít přidat nebo odebrat fotografie, vždy by ale alespoň jedna měla zůstat.

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
                    Settings.delimiter() + "Čas přípravy: " +
                    dish.getPreparationTimeInMinutes() + " min," +
                    Settings.delimiter() + "Fotografie: " +
                    dish.getImage() + "," +
                    Settings.delimiter() + "Kategorie: " +
                    dish.getCategory().getDescription());
            i++;
        }
        return returnString;
    }

}
