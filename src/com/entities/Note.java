package com.entities;

public class Note {

    private String title, text = "";

    public Note(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() { return text; }

    public void setText(String text) {
        this.text = text;
    }

    public void appendText(String text) {
        this.text += text;
    }

    @Override
    public String toString() {
        return title;
    }
}
