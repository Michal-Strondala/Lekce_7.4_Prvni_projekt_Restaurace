package com.engeto.first.project;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ReaderFromFile {

    public abstract void loadFromFile(String filename);

    // region Získání čísla stolu
    public int getTableNumber(String string) {
        int tableNumberFromStr = 0;
        Pattern pattern = Pattern.compile("stůl\\s+č\\.\\s+(\\d+)");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String strNumberOfTable = matcher.group(1); // Get the matched number as a string
            tableNumberFromStr = Integer.parseInt(strNumberOfTable); // Convert the string to an integer
        }
        return tableNumberFromStr;
    }
    // endregion

    // region Získání názvu pokrmu
    public String getNameFromString(String string) {
        String strName = ""; // Deklarování proměnné mimo if blok
        // Použití regexu k nalezení 'x' s číslem před tím
        boolean containsXWithNumber = string.matches(".*\\d+x.*");
        if (containsXWithNumber) {
            int leftDot =  string.indexOf(". ");
            int rightX =  string.indexOf("x");
            strName = string.substring(leftDot+1, rightX);
            // Odstranění přední mezery
            strName = strName.trim();
            // Odstranění následujícího čísla
            Pattern pattern = Pattern.compile("\\s+\\d+$");
            Matcher matcher = pattern.matcher(strName);
            if (matcher.find()) {
                strName = strName.substring(0, matcher.start());
            }
        } else {
            int leftDot =  string.indexOf(". ");
            int rightBracket =  string.indexOf(" (");
            strName = string.substring(leftDot+2, rightBracket);

        }
        return strName;
    }
    // endregion

    // region Získání ceny pokrmu
    public BigDecimal getPriceFromString(String string) throws RestaurantException {
        int leftBracketPos =  string.indexOf('(');
        int rightBracketPos =  string.indexOf(')');

        String amountString = string.substring(leftBracketPos+1, rightBracketPos);
        amountString = amountString.replace(" Kč", "");

        BigDecimal number = new BigDecimal(amountString);

        if (number.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RestaurantException("Nesprávně zadaná cena. Nesmí být menší než nula a nesmí se rovnat nule.");
        }

        return number;
    }
    // endregion

    // region Získání množství pokrmu
    public int getOrderedAmountFromString(String string) {
        int orderedAmountFromStr = 0;
        boolean containsXWithNumber = string.matches(".*\\d+x.*");
        if (containsXWithNumber) {
            // Extrahování čísla před 'x' pomocí regexu
            Pattern pattern = Pattern.compile("(\\d+)x");
            Matcher matcher = pattern.matcher(string);

            if (matcher.find()) {
                String amountStr = matcher.group(1); // Získání odpovídajícího čísla jako string
                orderedAmountFromStr = Integer.parseInt(amountStr); // Převedení stringu na integer
            }
        } else {
            orderedAmountFromStr = 1;
        }
        return orderedAmountFromStr;
    }
    // endregion

    // region Získání času objednání pokrmu
    public LocalTime getOrderedTimeFromString(String string) throws RestaurantException {
        LocalTime orderedTimeFromString = null;
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("HH:mm");
        // Extrahování času před '-' pomocí regexu
        Pattern pattern = Pattern.compile("(\\d{2}:\\d{2})-");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String timeStr = matcher.group(1); // Získání odpovídajícího čísla jako string
            orderedTimeFromString = LocalTime.parse(timeStr, parseFormat); // Převedení stringu na LocalTime
        }
        if (orderedTimeFromString == null) {
            throw new RestaurantException("Nesprávný formát času!");
        }
        return orderedTimeFromString;
    }
    // endregion

    // region Získání času vyřízení objednávky
    public LocalTime getFulfilmentTimeFromString(String string) throws RestaurantException {
        LocalTime fulfilmentTimeFromString = null;
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("HH:mm");
        // Extrahování času před '-' pomocí regexu
        Pattern pattern = Pattern.compile("-(\\d{2}:\\d{2})");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String timeStr = matcher.group(1); // Získání odpovídajícího čísla jako string
            if (timeStr.isEmpty()) {
                return null; // Time is empty, order is not completed
            } else {
                try {
                    fulfilmentTimeFromString = LocalTime.parse(timeStr, parseFormat); // Převedení stringu na LocalTime
                    if (!fulfilmentTimeFromString.isSupported(ChronoField.CLOCK_HOUR_OF_DAY) || !fulfilmentTimeFromString.isSupported(ChronoField.MINUTE_OF_HOUR)) {
                        throw new RestaurantException("Nesprávný formát času. Pokud není objednávka dokončena, nechte pole prázdné.");
                    }
                    return fulfilmentTimeFromString;
                } catch (DateTimeParseException e) {
                    throw new RestaurantException("Nesprávný formát času. Očekávaný formát je HH:mm.");
                }
            }
        }

        return null; // No fulfilment time found, order is not completed
    }
    // endregion

    // region Získání čísla číšníka zpracujícího tuto objednávku
    public int getWaiterNumberFromString(String string) throws RestaurantException {
        int waiterNumberFromStr = 0;
        // Extrahování času před '-' pomocí regexu
        Pattern pattern = Pattern.compile("\\s+(\\d+)$");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            String strWaiterNumber = matcher.group(1); // Získání odpovídajícího čísla jako string
            waiterNumberFromStr = Integer.parseInt(strWaiterNumber); // Převedení stringu na integer
        }
        if (waiterNumberFromStr == 0) {
            throw new RestaurantException("V souboru je chyba: Číšník má neplatné číslo.");
        }
        return waiterNumberFromStr;
    }
    // endregion
}
