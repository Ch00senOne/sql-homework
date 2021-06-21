package com.lab.models;

public class Periodical {
    private final int id;
    private final String title;

    public Periodical(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Periodical(String title) {
        this.title = title;
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
