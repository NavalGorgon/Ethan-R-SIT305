package com.example.task91p.data;

public class Advert {

    private String type, name, phone, details, date, location;
    private Integer id;
    private Double lat, lon;

    public Advert(Integer id, String type, String name, String phone, String details, String date, String location, Double lat, Double lon)
    {
        this.type = type;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.details = details;
        this.date = date;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
    };

    public String getDate() {
        return date;
    };

    public String getDetails() {
        return details;
    };

    public String getName() {
        return name;
    };

    public String getPhone() { return phone; };

    public String getLocation() { return location; };

    public String getType() { return type; }
    public Integer getID() { return id; }

    public void setDate(String date) {
        this.date = date;
    };

    public void setDetails(String details) {
        this.details = details;
    };

    public void setName(String name) {
        this.name = name;
    };

    public void setPhone(String phone) { this.phone = phone; };

    public void setLocation(String location) { this.location = location; };

    public void setType(String type) { this.type = type; }
    public void setID(Integer id) { this.id = id; }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
