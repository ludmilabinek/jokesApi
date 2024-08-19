package org.jokes.services;

import org.jokes.model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JokeService {
    public List<Joke> getJokes();
    public List<Joke> getJokesPaging(int limit, int offset);
    public Optional<Joke> getJokeById(UUID id);
    public List<Joke> getSearchJoke(String content, String type, String category, String[] exluded, String[] included);
    public UUID addJoke(JokeDTO newjoke);
    public boolean deleteJoke(UUID id);
    public boolean updateJoke(UUID id, JokeDTO newjoke);
}