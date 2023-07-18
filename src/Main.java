import com.engeto.first.project.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        // Vytvoření manažera
        RestaurantManager manager = new RestaurantManager();


        // v main bude, jen inicializace manageru a vypsání ze souboru nebo nastavení defaultních dat, nic více

        if ((Settings.fileExists(Settings.fileToBeRead()))) {
            System.out.println(Settings.textSeparator());
            System.out.println("Soubor existuje, načítam vše ze souboru " + Settings.fileToBeRead());
            manager.loadFromFile(Settings.fileToBeRead());
            manager.printAllOrders();
        } else {
            System.err.println("Soubor " + Settings.fileToBeRead() + " neexistuje, vytvořim defaultni data a soubor vytvořim");
            System.out.println(Settings.textSeparator());

            // toto udělá všechny operace z první části zadání
            manager.doAllFirstTasks();

            // tady jen test, zda se povedlo uložit soubor prvních tasků
//            System.out.println(Settings.textSeparator());
//            System.out.println("Načtené objednávky ze souboru:");
//            manager.clearOrders();
//            manager.loadFromFile(Settings.fileToBeRead());
//            manager.printAllOrders();
        }

        // toto udělá všechny operace z druhé části zadání:
        manager.doAllSecondTasks();

        // Uložení jídel do souboru zásobník-jídel.txt:
        try {
            manager.saveCookbookToFile(Settings.cookbookFileName());
        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
        // Vypsání zásobníku jídel do konzole:
        manager.printCookbook();
        // Uložení jídel do souboru zásobník-jídel.txt:
        try {
            manager.saveCurrentMenuToFile(Settings.menuFileName());
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