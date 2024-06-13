package org.jokes.model;

import java.util.UUID;

public class Joke {

    private UUID id;
    private JokeCategory category;
    private JokeType type;
    private String joke;

    public JokeCategory getCategory() {
        return category;
    }

    public void setCategory(JokeCategory category) {
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public JokeType getType() {
        return type;
    }

    public void setType(JokeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "category='" + category + '\'' +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", joke='" + joke + '\'' +
                '}';
    }

    public Joke() {
        this.id = UUID.randomUUID();
    }
}
