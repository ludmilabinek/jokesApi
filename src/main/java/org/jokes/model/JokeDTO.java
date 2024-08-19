package org.jokes.model;

import java.util.EnumMap;
import java.util.Map;

public class JokeDTO {
    private JokeCategory category;
    private String joke;
    private String secondPart;
    private Map<JokeFlags, Boolean> flags = new EnumMap<>(JokeFlags.class);

    public JokeCategory getCategory() {
        return category;
    }

    public void setCategory(JokeCategory category) {
        this.category = category;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public String getSecondPart() {
        return secondPart;
    }

    public void setSecondPart(String secondPart) {
        this.secondPart = secondPart;
    }

    public Map<JokeFlags, Boolean> getFlags() {
        return flags;
    }

    public void setFlags(Map<JokeFlags, Boolean> flags) {
        this.flags = flags;
    }

}