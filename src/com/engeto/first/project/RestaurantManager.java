package com.engeto.first.project;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager extends ReaderFromFile {

    List<Table> tablesList = new ArrayList<>();
    List<Waiter> waitersList = new ArrayList<>();
    List<Dish> dishesListFromFiles = new ArrayList<>();
    Cookbook cookbook = new Cookbook();
    Menu currentMenu = new Menu();


    // region Vytvoření seznamu objednávek pro jednotlivé stoly.

    private List<Orders> ordersLists = new ArrayList<>();

    public void addOrders(Orders orders) {
        this.ordersLists.add(orders);
    }

    // endregion

    // region Metoda k vypsání všech objednávek na jednostlivý stůl.
    public void printAllOrders() {

        for (Orders orders : ordersLists) {
            System.out.println("** Objednávky pro stůl č. " + orders.getTableNumber() + " **");
            System.out.print("\t****");
            System.out.println(orders);
            System.out.println("\t******");
        }
    }
    // endregion
    
    // region Metoda k vypsání zásobníku jídel (cookbook).
    public void printCookbook() {
        System.out.println(Settings.delimiter());
        System.out.println("** Zásobník jídel **");
        System.out.print("\t****");
        System.out.println(cookbook);
    }
    // endregion

    // region Metoda k vypsání aktuálního menu.
    public void printCurrentMenu() {
        System.out.println(Settings.delimiter());
        System.out.println("** Aktuální menu **");
        System.out.print("\t****");
        System.out.println(currentMenu);
    }
    // endregion

    // region *** Požadované výstupy ***
    // region 1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
    public void currentUnfinishedOrders() {
        int amountOfUnfinishedOrders = 0;
        for (Orders orders : this.ordersLists) {
            for (Order order : orders.getOrders()) {
                if (!order.isFinished()) {
                    amountOfUnfinishedOrders++;
                }

            }
        }
        System.out.println("\nPočet nedokončených objednávek je: " + amountOfUnfinishedOrders);
    }
    // endregion

    // region 2. Možnost seřadit objednávky podle číšníka nebo času zadání.
    // region 2.1 Seřazení podle času zadání (orderedTime)
    public void sortByOrderedTime() {
        for (Orders orders : this.ordersLists) {
            Collections.sort(orders.getOrders(), new OrderedTimeComparator());
        }
    }
    // endregion

    // region 2.2 Seřazení podle ID číšníka (waiterId)
    public void sortByWaiterId() {
        for (Orders orders : this.ordersLists) {
            Collections.sort(orders.getOrders(), new WaiterComparator());
        }
    }
    // endregion
    // endregion

    // region 3. Celkovou cenu objednávek pro jednotlivé číšníky (u každého číšníka bude počet jeho zadaných objednávek).
    // region 3.1 Celková cena na číšníka
    public BigDecimal getTotalPricePerWaiter(Waiter waiter) {
        BigDecimal totalPricePerWaiter = new BigDecimal(0);
        for (Orders orders : this.ordersLists) {
            for (Order order : orders.getOrders()) {
                if (order.getWaiterId() == waiter.getId())
                    totalPricePerWaiter = totalPricePerWaiter.add(order.getTotalPrice());
            }
        }
        return totalPricePerWaiter;
    }
    // endregion

    // region 3.2 Celkový počet objednávek na číšníka
    public int getTotalOrdersPerWaiter(Waiter waiter) {
        int totalOrdersPerWaiter = 0;
        for (Orders orders : this.ordersLists) {
            for (Order order : orders.getOrders()) {
                if (order.getWaiterId() == waiter.getId())
                    totalOrdersPerWaiter++;
            }
        }
        return totalOrdersPerWaiter;
    }
    // endregion

    // region 3.3 Výpis celkových cen objednávek a jejich počet na číšníka
    public void printTotalPriceAndOrdersPerWaiter() {
        System.out.println("\n*** Seznam číšníků a jejich statistiky ***");
        for (Waiter waiter : waitersList) {
            BigDecimal totalPrice = this.getTotalPricePerWaiter(waiter);
            int totalOrders = this.getTotalOrdersPerWaiter(waiter);

            System.out.println("\tČíšník č. " + waiter.getId() + " -> Celková cena objednávek = " + totalPrice + " Kč\t(počet objednávek: " + totalOrders + ")");
        }
        System.out.println("******");
    }
    // endregion
    // endregion

    // region 4. Průměrnou dobu zpracování objednávek, které byly zadány v určitém časovém období.

    public LocalTime[] readPeriod(String prompt, String minTimePrompt, String maxTimePrompt) {
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("HH:mm");
        Scanner sc = new Scanner(System.in);
        String timeString1;
        String timeString2;
        LocalTime time1;
        LocalTime time2;
        LocalTime minTime = LocalTime.parse(minTimePrompt, parseFormat);
        LocalTime maxTime = LocalTime.parse(maxTimePrompt, parseFormat);
        LocalTime[] timePeriod;
        while (true) {
            System.out.print(prompt);
            timeString1 = sc.nextLine();
            timeString2 = sc.nextLine();
            time1 = LocalTime.parse(timeString1, parseFormat);
            time2 = LocalTime.parse(timeString2, parseFormat);
            if ((time1.isAfter(minTime) && time2.isBefore(maxTime)) || (time2.isAfter(minTime) && time1.isBefore(maxTime)))
                break;
            System.out.println("Zapište čas v rozmezí od " + minTimePrompt + " do " + maxTimePrompt + ".");
        }
        timePeriod = new LocalTime[]{time1, time2};
        return timePeriod;
    }

    public void averageTimeOfCompletingOfOrders(LocalTime[] timePeriod) throws RestaurantException {
        LocalTime firstPromptedTime = timePeriod[0];
        LocalTime secondPromptedTime = timePeriod[1];
        int amountOfFinishedOrders = 0;
        long sumOfMinutes = 0;
        for (Orders orders : this.ordersLists) {
            for (Order order : orders.getOrders()) {
                if (order.isFinished()) {
                    if ((order.getOrderedTime().equals(firstPromptedTime) || order.getOrderedTime().isAfter(firstPromptedTime)) && (order.getOrderedTime().isBefore(secondPromptedTime))) {
                        if ((order.getFulfilmentTime().equals(secondPromptedTime) || order.getFulfilmentTime().isBefore(secondPromptedTime)) && (order.getFulfilmentTime().isAfter(firstPromptedTime))) {
                            sumOfMinutes += ChronoUnit.MINUTES.between(order.getOrderedTime(), order.getFulfilmentTime());
                            amountOfFinishedOrders++;
                        }
                    }
                }
            }
        }
        if (amountOfFinishedOrders == 0) {
            throw new RestaurantException("V tomto časovém rozmezí nebyla zpracována žádná objednávka.");
        } else
            System.out.println("\nPrůměrná doba zpracování objednávek v zadaném časovém rozmezí " + firstPromptedTime.toString() + "-" + secondPromptedTime.toString() + " je: " + (sumOfMinutes / amountOfFinishedOrders) + " minut.");
    }
    // endregion

    // region 5. Seznam jídel, které byly dnes objednány bez ohledu na to, kolikrát byly objednány.

    public void getListOfOrderedDishes() {
        Set<String> setOfOrderedDishes = new HashSet<>();
        System.out.println("Seznam jídel, které byly dnes objednány:\n");
        for (Orders orders : this.ordersLists) {
            for (Order order : orders.getOrders()) {
                setOfOrderedDishes.add(order.getDishFromCurrentMenu().getTitle());
            }
        }
        System.out.println("Počet objednaných jídel: " + setOfOrderedDishes.size());
        setOfOrderedDishes.forEach(System.out::println);
    }
    // endregion

    // region 6. Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku).

    public void saveListOfOrdersToFile(String filename, int tableNumber) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Orders orders : this.ordersLists) {
                if (orders.getTableNumber() == tableNumber) {
                    String tableNumberString = String.valueOf(orders.getTableNumber());
                    if (tableNumberString.length() < 2) {
                        tableNumberString = " " + tableNumberString;
                    }
                    writer.println("** Objednávky pro stůl č. " + tableNumberString + " **" +
                            "\n****" +
                            orders +
                            "\n******"
                    );
                }
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
    }
    // endregion
    // endregion

    // region Načtení zásobníku jídel do souboru
    public void saveCookbookToFile(String filename) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            int i = 1;
            writer.println("** Zásobník jídel **");
            for (Dish dish : this.cookbook) {
                writer.println("\n" +
                        i +
                        ". " +
                        dish.getTitle() +
                        " = Cena: " +
                        dish.getPrice() + " Kč" + "," +
                        Settings.delimiter() + "Čas přípravy: " +
                        dish.getPreparationTimeInMinutes() +
                        " min" + "," +
                        Settings.delimiter() + "Fotografie: " +
                        dish.getImage() + "," +
                        Settings.delimiter() + "Kategorie: " +
                        dish.getCategory().getDescription()
                );
                i++;
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
    }
    // endregion

    // region Načtení aktuálního menu do souboru
    public void saveCurrentMenuToFile(String filename) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            int i = 1;
            writer.println("** Aktuální menu **");
            for (Dish dish : this.currentMenu) {
                writer.println("\n" +
                        i +
                        ". " +
                        dish.getTitle() +
                        " = Cena: " +
                        dish.getPrice() + " Kč" + "," +
                        Settings.delimiter() + "Čas přípravy: " +
                        dish.getPreparationTimeInMinutes() +
                        " min" + "," +
                        Settings.delimiter() + "Fotografie: " +
                        dish.getImage() + "," +
                        Settings.delimiter() + "Kategorie: " +
                        dish.getCategory().getDescription()
                );
                i++;
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
    }
    // endregion

    // region Přístupové metody

    public Waiter getWaiter(int waiterId) throws RestaurantException {
        for (Waiter waiter : waitersList) {
            if (waiter.getId() == waiterId) {
                return waiter;
            }
        }
        throw new RestaurantException("Číšník s číslem: " + waiterId + " neexistuje.");
    }
    // Najmutí číšníků a přidání jich do seznamu číšníků:
    public void setWaiters(int waiterId) {
        this.waitersList.add(new Waiter(waiterId));
    }

    public Table getTable(int tableNumber) throws RestaurantException {
        for (Table table : tablesList) {
            if (table.getNumber() == tableNumber) {
                return table;
            }
        }
        throw new RestaurantException("Stůl s číslem: " + tableNumber + " neexistuje.");
    }
    // Vytvoření stolů:
    public void setTables(int tableNumber) {
        this.tablesList.add(new Table(tableNumber));
    }
    // endregion

    // region Metoda k načtení dat ze souboru
    @Override
    public void loadFromFile(String filename) {
        try {

            // Načtení souboru
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            int tableNumber = 0;
            Orders newOrdersFromFile = new Orders();

            // Procházení řádků
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Ověření zda jsem na řádku, kde je stůl:
                if(line.substring(0,3).equals("** "))
                {
                    tableNumber = this.getTableNumber(line);
                    newOrdersFromFile = new Orders(new Table(tableNumber));
                }

                // Ověření zda jsem na řádku, kde je jen oddělovač:
                if(line.substring(0,4).equals("****") && line.length()==4)
                {
                    continue;
                }

                // Ověření zda jsem na řádku s objednávkou:
                if(line.contains("číšník"))
                {
                    int orderedAmount = this.getOrderedAmountFromString(line);
                    BigDecimal price = this.getPriceFromString(line);
                    if (orderedAmount > 1) {
                        price = price.divide(BigDecimal.valueOf(orderedAmount), RoundingMode.HALF_UP);
                    }
                    String name = this.getNameFromString(line);
                    LocalTime orderedTime = this.getOrderedTimeFromString(line);
                    LocalTime fulfilmentTime = this.getFulfilmentTimeFromString(line);
                    int waiterNumber = this.getWaiterNumberFromString(line);

                    // Vytvoření jídla/pití
                    Dish dish = new Dish(name, price);
                    this.cookbook.add(dish);
                    this.currentMenu.add(dish);

                    // Vytvoření číšníka
                    Waiter waiter = new Waiter(waiterNumber);

                    // Vytvoření objednávky
                    Order order = new Order(tableNumber, waiter, orderedTime, fulfilmentTime, dish, orderedAmount, "");

                    // Vložení objednávky do seznamu objednávek
                    newOrdersFromFile.addOrder(order);

                }

                // Konec sekce
                if(line.substring(0,6).equals("******"))
                {
                    this.addOrders(newOrdersFromFile);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (RestaurantException e) {
            throw new RuntimeException(e);
        }
    }
    // endregion

    public void clearOrders() {
        this.ordersLists.clear();
    }


    // region Metoda, která provede všechny úkoly/pokyny z první části zadání projektu
    public void doAllFirstTasks() {
        Dish kofola;
        Dish pizzaGrande;
        Dish nanukMisa;
        try {
            System.out.println("*** Vytvoření a zpracování objednávek z PRVNÍ části projektu ***");
            System.out.println(Settings.textSeparator());
            // Vytvoření objektu dish
            kofola = new Dish(1000, "Kofola velká", BigDecimal.valueOf(32.5), 4, Category.DRINK);
            pizzaGrande = new Dish(2000, "Pizza Grande", BigDecimal.valueOf(130), 41, Category.FOOD);
            nanukMisa = new Dish(3000, "Nanuk Míša", BigDecimal.valueOf(30), 5, Category.FOOD);

            // Přidání dishe do cookbooku
            this.cookbook.add(kofola);
            this.cookbook.add(pizzaGrande);
            this.cookbook.add(nanukMisa);

            // Přidání dishe do Menu z cookbooku a výpis menu
            this.currentMenu.add(kofola);
            this.currentMenu.add(pizzaGrande);
            this.currentMenu.add(nanukMisa);

            // Najmutí číšníků a přidání jich do seznamu číšníků:
            setWaiters(2);
            setWaiters(3);
            setWaiters(4);

            // Vytvoření stolů
            setTables(4);

            // Vytvoření objednávky pro stůl č. 4
            Orders orderForTable4 = new Orders(getTable(4));

            if (currentMenu.isDishFromCurrentMenu(kofola)) {
                orderForTable4.addOrder(new Order(4, getWaiter(3), LocalTime.of(15, 25), LocalTime.of(15, 29), kofola, 4, ""));
            }
            if (currentMenu.isDishFromCurrentMenu(pizzaGrande)) {
                orderForTable4.addOrder(new Order(4, getWaiter(4), LocalTime.of(15, 29), LocalTime.of(16, 10), pizzaGrande, 1, ""));
            }
            if (currentMenu.isDishFromCurrentMenu(nanukMisa)) {
                orderForTable4.addOrder(new Order(4, getWaiter(2), LocalTime.of(17, 12), LocalTime.of(17, 18), nanukMisa, 1, ""));
            }
            addOrders(orderForTable4);

            // Výpis všech objednávek
            printAllOrders();

            // *** Požadované výstupy ***
            // 1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
            currentUnfinishedOrders();

            // 2.1 Seřazení podle času zadání objednávky(orderedTime)
            sortByOrderedTime();
            System.out.println("\n*** Seřazení podle času zadání objednávky ***");
            printAllOrders();

            // 2.2 Seřazení podle ID číšníka (waiterId)
            sortByWaiterId();
            System.out.println("\n*** Seřazení podle ID číšníka ***");
            printAllOrders();

            // 3. Výpis celkových cen objednávek a jejich počet na číšníka
            printTotalPriceAndOrdersPerWaiter();

            // 4. Průměrná doba zpracování objednávek, které byly zadány v určitém časovém období.
            //LocalTime[] timePeriod = readPeriod("Zadejte časové období pro výpočet průměrné doby zpracování objednávek (ve formátu HH:mm): ", "10:00", "22:00");
            //averageTimeOfCompletingOfOrders(timePeriod);

            // 5. Seznam jídel, které byly dnes objednány. Bez ohledu na to, kolikrát byly objednány.
            getListOfOrderedDishes();

            // 6. Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku).
            try {
                saveListOfOrdersToFile(Settings.firstOrdersFileName(), 4);
            } catch (RestaurantException e) {
                System.err.println(e.getLocalizedMessage());
            }

        } catch (RestaurantException ex) {
            throw new RuntimeException(ex);
        }
    }
    // endregion

        // region *** Čast zadání projektu "Testovací scénář: Kód pro ověření" ***
        // 1. Načti stav evidence z disku. Pokud se aplikace spouští poprvé a soubory neexistují, budou veškeré seznamy a repertoár zatím prázdné. (Aplikace nepotřebuje předvytvořené žádné soubory.)

        // region 2. Připrav testovací data. Vlož do systému:
        public void doAllSecondTasks() {
            try {
                System.out.println("*** Vytvoření a zpracování objednávek z DRUHÉ části projektu ***");
                System.out.println(Settings.textSeparator());
                // region 2.1 dva číšníky
                setWaiters(1);
                setWaiters(5);
                // endregion

                // region 2.2 tři stoly s čísly 1, 2 a 15.
                setTables(1);
                setTables(2);
                setTables(15);
                // endregion

                // region 2.3 tři jídla:
                // 2.3.1 Kuřecí řízek obalovaný 150 g
                Dish kurizek = new Dish(5000,"Kuřecí řízek obalovaný 150 g", BigDecimal.valueOf(175), 30, Category.FOOD);
                this.cookbook.add(kurizek);

                // 2.3.2 Hranolky 150 g
                Dish hranolky = new Dish(6000, "Hranolky 150 g",BigDecimal.valueOf(50), 20, Category.SIDE_DISH);
                this.cookbook.add(hranolky);

                // 2.3.3 Pstruh na víně 200 g
                Dish pstruh = new Dish(7000, "Pstruh na víně 200 g",BigDecimal.valueOf(215), 35, Category.FOOD);
                this.cookbook.add(pstruh);
                // endregion

                // region 2.4 První a třetí jídlo zařaď do aktuálního menu, druhé jídlo nikoli. Případné další můžeš zařadit dle potřeby.
                currentMenu.add(kurizek);
                currentMenu.add(pstruh);
                currentMenu.add(hranolky);
                // endregion

                // region 2.5 Vytvoř alespoň tři objednávky pro stůl číslo 15 a jednu pro stůl číslo 2.
                Orders orderForTable15 = new Orders(getTable(15));

                if (currentMenu.isDishFromCurrentMenu(kurizek)) {
                    orderForTable15.addOrder(new Order(15, getWaiter(1), LocalTime.of(20, 05), LocalTime.of(20, 40), kurizek, 1, ""));
                }
                if (currentMenu.isDishFromCurrentMenu(kurizek)) {
                    orderForTable15.addOrder(new Order(15, getWaiter(1), LocalTime.of(20, 05), LocalTime.of(20, 42), kurizek, 1, ""));
                }
                if (currentMenu.isDishFromCurrentMenu(pstruh)) {
                    orderForTable15.addOrder(new Order(15, getWaiter(1), LocalTime.of(20, 05), LocalTime.of(20,50 ), pstruh, 1, ""));
                }
                addOrders(orderForTable15);


                Orders orderForTable2 = new Orders(getTable(2));

                if (currentMenu.isDishFromCurrentMenu(pstruh)) {
                    orderForTable2.addOrder(new Order(2, getWaiter(5), LocalTime.of(19, 34), LocalTime.of(20, 19), pstruh, 2, ""));
                } else {
                    System.err.println("Toto jídlo není součástí aktuálního menu.");
                }
                if (currentMenu.isDishFromCurrentMenu(kurizek)) {
                    orderForTable2.addOrder(new Order(2, getWaiter(5), LocalTime.of(19, 35), LocalTime.of(20, 15), hranolky, 2, ""));
                } else {
                    System.err.println("Toto jídlo není součástí aktuálního menu.");
                }
                // endregion

                // region 3. Vyzkoušej přidat objednávku jídla, které není v menu — aplikace musí ohlásit chybu.
                if (currentMenu.isDishFromCurrentMenu(hranolky)) {
                    orderForTable2.addOrder(new Order(2, getWaiter(5), LocalTime.of(19, 35), LocalTime.of(20, 14), hranolky, 2, ""));
                } else {
                    System.err.println("Toto jídlo není součástí aktuálního menu.");
                }
                addOrders(orderForTable2);
                // endregion

                // region 4. Proveď uzavření objednávky.
                // endregion

                // region 5. Použij všechny připravené metody pro získání informací pro management — údaje vypisuj na obrazovku.
                // Výpis všech objednávek
                printAllOrders();
                // 5.1 Kolik objednávek je aktuálně rozpracovaných a nedokončených.
                currentUnfinishedOrders();
                // 5.2 Seřazení podle času zadání objednávky(orderedTime)
                sortByOrderedTime();
                System.out.println("\n*** Seřazení podle času zadání objednávky ***");
                printAllOrders();
                // 5.3 Seřazení podle ID číšníka (waiterId)
                sortByWaiterId();
                System.out.println("\n*** Seřazení podle ID číšníka ***");
                printAllOrders();
                // 5.4 Výpis celkových cen objednávek a jejich počet na číšníka
                printTotalPriceAndOrdersPerWaiter();
                // 5.5 Průměrná doba zpracování objednávek, které byly zadány v určitém časovém období.
                //LocalTime[] timePeriod = readPeriod("Zadejte časové období pro výpočet průměrné doby zpracování objednávek (ve formátu HH:mm): ", "10:00", "22:00");
                //averageTimeOfCompletingOfOrders(timePeriod);
                // 5.6 Seznam jídel, které byly dnes objednány. Bez ohledu na to, kolikrát byly objednány.
                getListOfOrderedDishes();
                // 5.7 Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku).
                printAllOrders();
                // endregion

                // region 6. Změněná data ulož na disk. Po spuštění aplikace musí být data opět v pořádku načtena.
                try {
                    saveListOfOrdersToFile(Settings.secondOrdersFileName(), 15);
                    saveListOfOrdersToFile(Settings.secondOrdersFileName(), 2);
                } catch (RestaurantException e) {
                    System.err.println(e.getLocalizedMessage());
                }
                // endregion
            } catch (RestaurantException e) {
                throw new RuntimeException(e);
            }
        }
        // endregion
    // endregion

        // region Metoda k odstranění jídla/pití z aktuálního menu
    public void removeDishFromCurrentMenu(String name) throws RestaurantException {
        try {
            boolean removed = false;

            Iterator<Dish> iterator = currentMenu.iterator();
            while (iterator.hasNext()) {
                    Dish dish = iterator.next();
                    if (dish.getTitle().contains(name)) {
                        iterator.remove();
                        removed = true;
                        System.out.println("\n" + name + ": úspěšně odebráno z aktuálního menu!\n");
                    }
                }
            if (!removed) {
                System.err.println("Žádné jídlo/pití s názvem \"" + name + "\" nebylo v seznamu nalezeno. Napište celé jméno!");
            }
        } catch (IllegalStateException e) {
            throw new RestaurantException("Zkuste jiný název jídla/pití.");
        }
    }
    // endregion

    // region Metoda k odstranění jídla/pití ze zásobníku jídel
    public void removeDishFromCookbook(String name) throws RestaurantException {
        try {
            boolean removed = false;

            Iterator<Dish> iterator = cookbook.iterator();
            while (iterator.hasNext()) {
                Dish dish = iterator.next();
                if (dish.getTitle().contains(name)) {
                    iterator.remove();
                    removed = true;
                    System.out.println("\n" + name + ": úspěšně odebráno ze zásobníku jídel!\n");
                }
            }
            if (!removed) {
                System.err.println("Žádné jídlo/pití s názvem \"" + name + "\" nebylo v seznamu nalezeno. Napište celé jméno!");
            }
        } catch (IllegalStateException e) {
            throw new RestaurantException("Zkuste jiný název jídla/pití.");
        }
    }
    // endregion

    // region Metoda k úpravě nějakého pokrmu/pití
    public void updateDish(String title, BigDecimal price, int preparationTime, String image, Category category) throws RestaurantException {
        try {
            Iterator<Dish> iterator = cookbook.iterator();
            while (iterator.hasNext()) {
                Dish dish = iterator.next();
                if (dish.getTitle().contains(title)) {
                    dish.setTitle(title);
                    dish.setPrice(price);
                    dish.setPreparationTimeInMinutes(preparationTime);
                    dish.setImage(image);
                    dish.setCategory(category);
                    System.out.println("\n" + title + ": upraveno\n");
                }
            }
        } catch (Exception e) {
            throw new RestaurantException("Chyba při aktualizaci jídla: " + e.getMessage());
        }
    }
    // endregion

}

