import com.engeto.first.project.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        // Vytvoření manažera
        RestaurantManager manager = new RestaurantManager();


        // v main bude, jen inicializace manageru a vypsání ze souboru nebo nastavení defaultních dat, nic více

        if (Settings.fileExists(Settings.FILE_TO_BE_READ)) {
            System.out.println(Settings.TEXT_SEPARATOR);
            System.out.println("Soubor existuje, načítam vše ze souboru " + Settings.FILE_TO_BE_READ);
            manager.loadFromFile(Settings.FILE_TO_BE_READ);
            manager.printAllOrders();
        } else {
            System.err.println("Soubor " + Settings.FILE_TO_BE_READ + " neexistuje, vytvořim defaultni data a soubor vytvořim");
            System.out.println(Settings.TEXT_SEPARATOR);

            // toto udělá všechny operace z první části zadání
            manager.doAllFirstTasks();
        }

        // toto udělá všechny operace z druhé části zadání:
        manager.doAllSecondTasks();

        // Uložení jídel do souboru zásobník-jídel.txt:
        try {
            manager.saveCookbookToFile(Settings.COOKBOOK_FILENAME);
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
        // Vypsání zásobníku jídel do konzole:
        manager.printCookbook();
        // Uložení jídel do souboru zásobník-jídel.txt:
        try {
            manager.saveCurrentMenuToFile(Settings.MENU_FILE_NAME);
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
        // Test odstranění limonády z aktuálního menu:
        try {
            manager.removeDishFromCurrentMenu("Limonáda");
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
        // Vypsání aktuálního menu a poté zásobníku jídel do konzole:
        manager.printCurrentMenu();
        manager.printCookbook();

        // Test odstranění kávy ze zásobníku jídel:
        try {
            manager.removeDishFromCookbook("Káva");
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }

        // Vypsání zásobníku jídel do konzole:
        manager.printCookbook();

        // Úprava jídla v zásobníku jídel:
        try {
            manager.updateDish("Pivo Radegast", BigDecimal.valueOf(40), 10, "pivo-radegast-01", Category.DRINK);
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
        // Vypsání zásobníku jídel do konzole:
        manager.printCookbook();

    }
}