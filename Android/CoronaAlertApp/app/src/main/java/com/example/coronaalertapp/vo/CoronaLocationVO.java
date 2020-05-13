package com.example.coronaalertapp.vo;

import java.io.Serializable;

public class CoronaLocationVO implements Serializable {

    int no;
    String name;
    double lat;
    double lon;

    String patientName;

    public CoronaLocationVO() {
    }

    public CoronaLocationVO(int no, String name, double lat, double lon) {
        this.no = no;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public CoronaLocationVO(int no, String name, double lat, double lon, String patientName) {
        this.no = no;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.patientName = patientName;
    }


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return "CoronaLocation{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", paintName='" + patientName + '\'' +
                '}';
    }
}
