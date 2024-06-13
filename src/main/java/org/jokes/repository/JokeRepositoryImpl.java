package org.jokes.repository;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeType;
import org.springframework.stereotype.Repository;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
public class JokeRepositoryImpl implements JokeRepository{

    private Map<UUID, Joke> jokeList = new HashMap<>();

    @Override
    public Map<UUID, Joke> findAll() {
        return jokeList;
    }

    @Override
    public Joke findById(UUID id) {
        if(jokeList.containsKey(id)) {
            return  jokeList.get(id);
        }
        else {
            return null;
        }
    }

    @Override
    public Map<UUID, Joke> findByContent(String content) {
        Map<UUID, Joke> resultList = jokeList.entrySet().stream()
                .filter(map -> map.getValue().getJoke().contains(content))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        if(!resultList.isEmpty()) {
            return  resultList;
        }
        else {
            return null;
        }
    }

    @Override
    public Map<UUID, Joke> findByType(JokeType content) {
        Map<UUID, Joke> resultList = jokeList.entrySet().stream()
                .filter(map -> map.getValue().getType().equals(content))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        if(!resultList.isEmpty()) {
            return  resultList;
        }
        else {
            return null;
        }
    }

    @Override
    public Map<UUID, Joke> findByCategory(JokeCategory content) {
        Map<UUID, Joke> resultList = jokeList.entrySet().stream()
                .filter(map -> map.getValue().getCategory().equals(content))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        if(!resultList.isEmpty()) {
            return  resultList;
        }
        else {
            return null;
        }
    }

    @Override
    public UUID save(Joke joke) {
        Joke newJoke = new Joke();
        newJoke.setJoke(joke.getJoke());
        newJoke.setCategory(joke.getCategory());
        newJoke.setType(joke.getType());
        jokeList.put(newJoke.getId(), newJoke);
        return newJoke.getId();
    }

    @Override
    public boolean delete(UUID id) {
        if(jokeList.containsKey(id)) {
            if(jokeList.remove(id)!=null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean update(UUID id, Joke newjoke) {
        if(jokeList.containsKey(id)) {
            jokeList.replace(id, newjoke);
            if(jokeList.get(id).equals(newjoke)) {
                return true;
            }
        }

        return false;
    }
}
