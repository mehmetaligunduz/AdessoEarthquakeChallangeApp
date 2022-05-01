package com.adesso;

import java.util.List;
import java.util.Scanner;

public class App {
    public void IOOperation() {
        String country = "";
        int countDays = 0;
        Scanner sc = new Scanner(System.in);
        boolean flag = false;
        do {
            System.out.print("Country: ");
            country = sc.nextLine();
            if (country.trim().toLowerCase().equals("x"))
                return;
            System.out.print("Count of days: ");
            countDays = sc.nextInt();
            showDataTable(country, countDays);
            sc.nextLine();
        } while (!country.trim().toLowerCase().equals("x"));
    }

    public void showDataTable(String country, int countDays) {
        ApiImplementation apiImplementation = new ApiImplementation();
        try {
            String leftAlignFormat = "| %-15s | %-55s | %-9s | %-10s |%n";

            System.out.format("+-----------------+---------------------------------------------------------+-----------+-------------------------------+%n");
            System.out.format("| Country         | Place                                                   | Magnitude | Date                          |%n");
            System.out.format("+-----------------+---------------------------------------------------------+-----------+-------------------------------+%n");

            List<Earthquake> list = apiImplementation.getEarthquakes(country, countDays);
            for (Earthquake item : list) {
                System.out.format(leftAlignFormat, item.getCountry(), item.getPlace(), item.getMagnitude(), item.getDateAndTime());
            }

            System.out.format("+-----------------+---------------------------------------------------------+-----------+-------------------------------+%n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
