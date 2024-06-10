package org.jokes.repository;

import org.jokes.model.Joke;

import java.util.HashMap;



public interface JokeRepository {
    public HashMap<Integer, Joke> findAll();
    public Joke findById(int id);
    public Joke save(Joke joke);
    public boolean delete(int id);
    public boolean update(int id, Joke joke);
}