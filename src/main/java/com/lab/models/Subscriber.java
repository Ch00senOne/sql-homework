package com.lab.models;

public class Subscriber {
    private final String name;
    private final int id;

    public Subscriber(int id, String name) {
        this.id = id;this.name = name;

    }



    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
