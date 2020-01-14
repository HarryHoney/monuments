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
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    String name;
    String icon;
    double distance;
    double degree;

    public PointsData() {
    }

    public PointsData(String name, String icon, double distance, double degree) {
        this.name = name;
        this.icon = icon;
        this.distance = distance;
        this.degree = degree;
    }



}
