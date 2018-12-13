package com.example.duncan.triviaapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    String question, answer;
    ArrayList<String> incorrect;

    public Question(String question, String answer, ArrayList<String> incorrect) {
        this.question = question;
        this.answer = answer;
        this.incorrect = incorrect;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {this.question = question;}
    public String getAnswer() { return answer; }
    public ArrayList<String> getIncorrect() { return incorrect; }
}