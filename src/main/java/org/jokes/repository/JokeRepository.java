package org.jokes.repository;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeType;

import java.util.Map;
import java.util.UUID;


public interface JokeRepository {
    public Map<UUID, Joke> findAll();
    public Joke findById(UUID id);
    public Map<UUID, Joke> findByContent(String content);
    public Map<UUID, Joke> findByType(JokeType content);
    public Map<UUID, Joke> findByCategory(JokeCategory content);
    public UUID save(Joke joke);
    public boolean delete(UUID id);
    public boolean update(UUID id, Joke joke);
}