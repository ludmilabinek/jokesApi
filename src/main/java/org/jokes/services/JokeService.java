package org.jokes.services;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface JokeService {
    public Map<UUID, Joke> getJokes();
    public Optional<Joke> getJokeById(UUID id);
    public Map<UUID, Joke> getJokeByContent(String content);
    public Map<UUID, Joke> getJokeByType(JokeType content);
    public Map<UUID, Joke> getJokeByCategory(JokeCategory content);
    public UUID addJoke(Joke newjoke);
    public boolean deleteJoke(UUID id);
    public boolean updateJoke(UUID id, Joke newjoke);
}
