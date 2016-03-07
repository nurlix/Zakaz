package com.nurlan.zakaz.Objects;

/**
 * Created by NURLAN on 17.12.2015.
 */
public class Agent {

    int id;
    String name;

    public void Agent() {}

    public void Agent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
