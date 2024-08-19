package org.jokes.repository;

import org.jokes.model.Joke;

import java.util.List;
import java.util.UUID;


public interface JokeRepository {
    public List<Joke> findAll();
    public List<Joke> findPaging(int limit, int offset);
    public Joke findById(UUID id);
    public List<Joke> searchJoke(String content, String type, String category, String[] exluded, String[] included);
    public UUID save(Joke joke);
    public boolean delete(UUID id);
    public boolean update(UUID id, Joke joke);
}