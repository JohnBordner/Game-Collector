package com.example.mobileapp.model;

public class Game {
    private String title;
    private int year;
    private String console;

    public Game(String title, int year, String console){
        this.title = title;
        this.year = year;
        this.console = console;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }
}
