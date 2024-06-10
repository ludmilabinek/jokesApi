package org.jokes.services;

import org.jokes.model.Joke;

import java.util.HashMap;
import java.util.Optional;

public interface JokeService {
    public HashMap<Integer, Joke> getJokes();
    public Optional<Joke> getJokeById(int id);
    public Joke addJoke(Joke newjoke);
    public boolean deleteJoke(int id);
    public boolean updateJoke(int id, Joke newjoke);
}
