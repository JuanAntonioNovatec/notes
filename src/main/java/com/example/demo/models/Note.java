package com.example.demo.models;

public class Note {
    private String text;

    public Note() {}

    public Note (String newText) {
        this.text = newText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}