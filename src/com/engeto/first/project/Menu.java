package com.engeto.first.project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Menu extends Cookbook{

    public void saveMenuToFile(String filename, List<Dish> menu) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Dish dish : menu) {
                writer.println(dish.toString());
            }
        } catch (IOException e) {
            throw new RestaurantException("Nastala chyba při ukládání aktuálního menu do souboru.");
        }
    }

    public boolean isDishFromCurrentMenu(Dish dish) {
        for (Dish dishIndex : this) {
            if (dishIndex.getDishId() == dish.getDishId()) {
                return true;
            }
        }
        return false;
    }
}
