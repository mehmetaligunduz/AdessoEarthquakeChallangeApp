package com.EarthquakeApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ApiImplementation implements Api {
    
    @Override
    public List<Earthquake> getEarthquakes(String country, int countDays) throws IOException, ParseException {
        List<Earthquake> earthquakeList = new ArrayList<>();
        JSONParser parse = new JSONParser();
        JSONObject responseJSON = (JSONObject) parse.parse(getResponse(countDays));
        JSONArray featuresArrayJSON = (JSONArray) responseJSON.get("features");

        String modelCountry = "";
        String place = "";
        int countryCounter = 0;

        for (int i = 0; i < featuresArrayJSON.size(); i++) {

            JSONObject objectJSON = (JSONObject) featuresArrayJSON.get(i);
            JSONObject propertiesObjectJSON = (JSONObject) objectJSON.get("properties");
            String placeJSON = (String) propertiesObjectJSON.get("place");
            String magJSON = propertiesObjectJSON.get("mag").toString();
            long timeJSON = (long) propertiesObjectJSON.get("time");

            if (placeJSON != null) {
                String[] splittingPlace = placeJSON.split(",");
                if (splittingPlace.length > 1) {
                    modelCountry = splittingPlace[1];
                    place = splittingPlace[0];
                } else {
                    modelCountry = placeJSON;
                    place = placeJSON;
                }
                if (modelCountry.trim().toLowerCase().equals(country.toLowerCase())) {
                    countryCounter++;
                    earthquakeList.add(new Earthquake(modelCountry.trim(), place, magJSON, timeJSON));
                }
            }
        }
        if (countryCounter == 0)
            System.out.println("No earthquakes were recorded past " + countDays + " days");
        return earthquakeList;
    }

    @Override
    public String getResponse(int countDays) throws IOException {
        URL url = new URL(generateURL(countDays));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        String response = "";
        if (responseCode == 400) {
            System.out.println("Exceeds search limit of 20000. Modify the search to match fewer events.");
        } else if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();
        }
        return response;
    }

    /*
    * param int countDays
    * return String
    * it is a helper method we should calculate past {x} days date
    * in the method we take countDays as parameter and subtract it from
    * current date when we look at the URL (https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2022-01-01&endtime=2022-01-02)
    * There are two field starttime and endtime, endtime is current date and
    * starttime is current time -(minus) countDays
    * the method generate endtime and return it.
    * */
    private String helperGetStartDate(int countDays) {
        LocalDate daySubtraction = LocalDate.now().minusDays(countDays);
        return daySubtraction.toString();
    }

    /*
    * return string
    * it returns current date for endtime in the URL
    * */
    private String helperGetEndDate() {
        return LocalDate.now().toString();
    }

    /*
    * param countDays
    * return String
    * it concatenate URL with startime and endtime and return rearranged URL
    * */
    private String generateURL(int countDays) {
        return ("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=" + helperGetStartDate(countDays) + "&endtime=" + helperGetEndDate());
    }
}
