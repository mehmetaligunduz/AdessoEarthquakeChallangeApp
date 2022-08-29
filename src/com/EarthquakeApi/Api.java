package com.EarthquakeApi;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface Api {
    /*
    * param String country
    * param int countDays
    * return list<Earthquake>
    * throws IOException, ParseException
    * It takes response from getResponse method and filter it according to country name (Alaska, CA, Chile, Turkey...)
    * if there is no any record in past {x} days it warns you
    * the api has search limit(20000) if your searching, exceeds the search limit it also warns you
    */
    public List<Earthquake> getEarthquakes(String country, int countDays) throws IOException, ParseException;
    
    /*
    * param int countDays
    * return String
    * throws IOException
    * It takes response from api and return that response.
    */
    public String getResponse(int countDays) throws IOException;

}
