package org.jokes.model;

import org.jokes.utils.JokeUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class Joke {

    private UUID id;
    private JokeCategory category;
    private JokeType type;
    private String joke;
    private String secondPart;
    private LocalDateTime createData;
    private Map<JokeFlags, Boolean> jokeFlagsMap;

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

    public String getSecondPart() {
        return secondPart;
    }

    public void setSecondPart(String secondPart) {
        this.secondPart = secondPart;
    }

    public LocalDateTime getCreateData() {
        return createData;
    }

    public void setCreateData(LocalDateTime createData) {
        this.createData = createData;
    }

    public Map<JokeFlags, Boolean> getJokeFlagsMap() {
        return jokeFlagsMap;
    }

    public void setJokeFlagsMap(Map<JokeFlags, Boolean> jokeFlagsMap) {
        this.jokeFlagsMap = jokeFlagsMap;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "category='" + category + '\'' +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", joke='" + joke + '\'' +
                ", secondPart='" + secondPart + '\'' +
                ", createData='" + createData.toString() + '\'' +
                ", jokeFlagsMap='" + jokeFlagsMap.toString() + '\'' +
                '}';
    }

    public Joke() {
        this.id = UUID.randomUUID();
        this.createData = LocalDateTime.now();
        this.jokeFlagsMap = JokeUtils.createJokeFlagsMap(false, false, false, false, false, false);
    }
}