package com.example.harpreet.visitorguide.UtilsFolder;

public class PointsData {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDegree() {
        return degrees;
    }

    public void setDegrees(double degree) {
        this.degrees = degree;
    }

    String name;
    String icon;
    double distance;
    double degrees;

    public double getDegrees() {
        return degrees;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    String place_id;

    public PointsData() {
    }

    public PointsData(String name, String icon, double distance, double degree,String place_id) {
        this.name = name;
        this.icon = icon;
        this.distance = distance;
        this.degrees = degree;
        this.place_id = place_id;
    }



}
