package com.EarthquakeApi;

import java.util.Date;

public class Earthquake {
    private String country;
    private String place;
    private String magnitude;
    private Date dateAndTime;

    public Earthquake(String country, String place, String magnitude, long dateAndTime) {
        this.country = country;
        this.place = place;
        this.magnitude = magnitude;
        this.dateAndTime = new Date(System.currentTimeMillis() + (dateAndTime * 1000));
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getCountry() {
        return country;
    }

    public String getPlace() {
        return place;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "country='" + country + '\'' +
                ", place='" + place + '\'' +
                ", magnitude='" + magnitude + '\'' +
                ", dateAndTime=" + dateAndTime +
                '}';
    }
}
