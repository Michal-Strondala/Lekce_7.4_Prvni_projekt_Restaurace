package com.engeto.first.project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {
    List<Waiter> waitersList = new ArrayList<>();


    // Vytvoření seznamu objednávek pro jednotlivé stoly.

    private List<Orders> ordersLists = new ArrayList<>();

    public void addOrders(Orders orders) {
        this.ordersLists.add(orders);
    }

    // Metoda k vypsání všech objednávek na jednostlivý stůl.
    public void printAllOrders() {

        for (Orders orders : ordersLists) {
            System.out.println("** Objednávky pro stůl č. " + orders.getTableNumber() + " **");
            System.out.print("\t****");
            System.out.println(orders);
            System.out.println("\t******");
        }
    }

    // *** Požadované výstupy ***
    // 1. Kolik objednávek je aktuálně rozpracovaných a nedokončených.
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

    // 2. Možnost seřadit objednávky podle číšníka nebo času zadání.

    // 2.1 Seřazení podle času zadání (orderedTime)
    public void sortByOrderedTime() {
        for (Orders orders : this.ordersLists) {
            Collections.sort(orders.getOrders(), new OrderedTimeComparator());
        }
    }

    // 2.2 Seřazení podle ID číšníka (waiterId)
    public void sortByWaiterId() {
        for (Orders orders : this.ordersLists) {
            Collections.sort(orders.getOrders(), new WaiterComparator());
        }
    }


    // 3. Celkovou cenu objednávek pro jednotlivé číšníky (u každého číšníka bude počet jeho zadaných objednávek).
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

    // Výpis celkových cen objednávek a jejich počet na číšníka
    public void printTotalPriceAndOrdersPerWaiter() {
        System.out.println("\n*** Seznam číšníků a jejich statistiky ***");
        for (Waiter waiter : waitersList) {
            BigDecimal totalPrice = this.getTotalPricePerWaiter(waiter);
            int totalOrders = this.getTotalOrdersPerWaiter(waiter);

            System.out.println("\tČíšník č. " + waiter.getId() + " -> Celková cena objednávek = " + totalPrice + " Kč\t(počet objednávek: " + totalOrders + ")");
        }
        System.out.println("******");
    }

    // 4. Průměrnou dobu zpracování objednávek, které byly zadány v určitém časovém období.

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

    // 5. Seznam jídel, které byly dnes objednány. Bez ohledu na to, kolikrát byly objednány.

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


    // 6. Export seznamu objednávek pro jeden stůl ve formátu (například pro výpis na obrazovku).

    public void saveListOfOrdersToFile(String filename) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Orders orders : this.ordersLists) {
                writer.println("** Objednávky pro stůl č. " + orders.getTableNumber() + " **" +
                        "\n****" +
                        orders +
                        "\n******"
                );
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru " + filename + ": " + e.getLocalizedMessage());
        }
    }


    // Přístupové metody
    public void setWaiters() {

        // Najmutí číšníků a přidání jich do seznamu číšníků:
        waitersList.add(new Waiter(2));
        waitersList.add(new Waiter(3));
        waitersList.add(new Waiter(4));
    }

    public Waiter getWaiter(int waiterId) throws RestaurantException {
        for (Waiter waiter : waitersList) {
            if (waiter.getId() == waiterId) {
                return waiter;
            }
        }
        throw new RestaurantException("Číšník s číslem: " + waiterId + " neexistuje.");
    }

}
