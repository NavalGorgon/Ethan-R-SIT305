package com.example.task41p;

public class Task {

    private String title, details, date;

    public Task(String title, String details, String date)
    {
        this.title = title;
        this.details = details;
        this.date = date;
    };

    public String getDate() {
        return date;
    };

    public String getDetails() {
        return details;
    };

    public String getTitle() {
        return title;
    };

    public void setDate(String date) {
        this.date = date;
    };

    public void setDetails(String details) {
        this.details = details;
    };

    public void setTitle(String title) {
        this.title = title;
    };

}
