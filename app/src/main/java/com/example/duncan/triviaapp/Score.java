package com.example.duncan.triviaapp;

import java.io.Serializable;

public class Score implements Serializable {

    String name;
    String points;

    public Score(String name, String points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String question) {this.name = name;}
    public String getPoints() { return points; }
    public void setPoints(String points) {this.points = points;}
}
