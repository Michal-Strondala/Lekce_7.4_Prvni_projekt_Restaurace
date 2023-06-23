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

    public void saveDishesToFile(String filename, List<Dish> cookbook) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Dish dish : cookbook) {
                writer.println(dish.toString());
            }
        } catch (IOException e) {
            throw new RestaurantException("Nastala chyba při ukládání zásobníku jídla do souboru.");
        }
    }

}
