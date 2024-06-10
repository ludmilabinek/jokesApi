package org.jokes.model;

public class Joke {

    private int id;
    private String category;
    private String type;
    private String joke;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
