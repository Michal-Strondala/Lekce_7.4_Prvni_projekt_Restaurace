import com.engeto.first.project.*;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

//        ** Objednávky pro stůl č.  4 **
//****
//        1. Kofola velká 4x (130 Kč):    15:25-15:29 číšník č. 3
//        2. Pizza Grande (130 Kč):   15:29-16:10 číšník č. 4
//        3. Nanuk Míša (30 Kč):  17:12-17:18 číšník č. 2
        try {

            // Vytvoření objektu dish
            Dish kofola = new Dish(1000, "Kofola velká", 32.5, 4, Category.DRINK);
            Dish pizzaGrande = new Dish(2000, "Pizza Grande", 130, 41, Category.FOOD);
            Dish nanukMisa = new Dish(3000, "Nanuk Míša", 30, 5, Category.FOOD);
            Dish hranolky = new Dish(4000, "Hranolky", 25, 20, Category.SIDE_DISH);

            // Přidání dishe do cookbooku
            Cookbook cookbook = new Cookbook();
            cookbook.add(kofola);
            cookbook.add(pizzaGrande);
            cookbook.add(nanukMisa);
            cookbook.add(hranolky);

            // Přidání dishe do Menu z cookbooku a výpis menu
            Menu currentMenu = new Menu();
            currentMenu.add(kofola);
            currentMenu.add(pizzaGrande);
            currentMenu.add(nanukMisa);
            currentMenu.add(hranolky);

            // Vytvoření manažera
            RestaurantManager manager = new RestaurantManager();
            manager.setWaiters();

            // Vytvoření objednávky pro stůl č. 4
            Orders orderForTable4 = new Orders(4);

            if(currentMenu.isDishFromCurrentMenu(kofola))
            {
                orderForTable4.addOrder(new Order(4, manager.getWaiter(3), LocalTime.of(15,25), LocalTime.of(15, 29), kofola, 4,""));
            }
            if(currentMenu.isDishFromCurrentMenu(pizzaGrande))
            {
                orderForTable4.addOrder(new Order(4, manager.getWaiter(4), LocalTime.of(15,29), LocalTime.of(16,10), pizzaGrande, 1,""));
            }
            if(currentMenu.isDishFromCurrentMenu(nanukMisa))
            {
                orderForTable4.addOrder(new Order(4, manager.getWaiter(2), LocalTime.of(17,12), LocalTime.of(17, 18),nanukMisa, 1,""));
            }
            manager.addOrders(orderForTable4);

            // Vytvoření objednávky pro stůl č. 1
            Orders orderForTable1 = new Orders(1);

            if(currentMenu.isDishFromCurrentMenu(kofola))
            {
                orderForTable1.addOrder(new Order(1, manager.getWaiter(2), LocalTime.of(13,47), kofola, 2,""));
            }
            if(currentMenu.isDishFromCurrentMenu(hranolky))
            {
                orderForTable1.addOrder(new Order(1, manager.getWaiter(3), LocalTime.of(13,53), hranolky, 1,""));
            }
            manager.addOrders(orderForTable1);

            // Výpis všech objednávek
            manager.printAllOrders();

            // *** Požadované výstupy ***
            // 1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
            manager.currentUnfinishedOrders();

            // 2.1 Seřazení podle času zadání objednávky(orderedTime)
            manager.sortByOrderedTime();
            System.out.println("\n*** Seřazení podle času zadání objednávky ***");
            manager.printAllOrders();

            // 2.2 Seřazení podle ID číšníka (waiterId)
            manager.sortByWaiterId();
            System.out.println("\n*** Seřazení podle ID číšníka ***");
            manager.printAllOrders();

            // 3. Výpis celkových cen objednávek a jejich počet na číšníka
            manager.printTotalPriceAndOrdersPerWaiter();

            // 4. Průměrná doba zpracování objednávek, které byly zadány v určitém časovém období.
            //LocalTime[] timePeriod = manager.readPeriod("Zadejte časové období pro výpočet průměrné doby zpracování objednávek (ve formátu HH:mm): ", "10:00", "22:00");
            //manager.averageTimeOfCompletingOfOrders(timePeriod);

            // 5. Seznam jídel, které byly dnes objednány. Bez ohledu na to, kolikrát byly objednány.
            manager.getListOfOrderedDishes();

            // 6. Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku).
            try {
                manager.saveListOfOrdersToFile(Settings.ordersFileName());
            } catch (RestaurantException e) {
                System.err.println(e.getLocalizedMessage());
            }




        } catch (RestaurantException e) {
            throw new RuntimeException(e);
        }

    }
}