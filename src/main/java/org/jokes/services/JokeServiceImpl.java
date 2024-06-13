package org.jokes.services;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeType;
import org.jokes.repository.JokeRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class JokeServiceImpl implements JokeService{

    private final JokeRepository jokeRepository;

    public JokeServiceImpl(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public Map<UUID, Joke> getJokes() {
        Map<UUID, Joke> jokeList = jokeRepository.findAll();
        return jokeList;
    }

    @Override
    public Optional<Joke> getJokeById(UUID id) {
        Optional<Joke> joke = Optional.ofNullable(jokeRepository.findById(id));
        return joke;
    }

    @Override
    public Map<UUID, Joke> getJokeByContent(String content) {
        Map<UUID, Joke> jokeList = jokeRepository.findByContent(content);
        return jokeList;
    }

    @Override
    public Map<UUID, Joke> getJokeByType(JokeType content) {
        Map<UUID, Joke> jokeList = jokeRepository.findByType(content);
        return jokeList;
    }

    @Override
    public Map<UUID, Joke> getJokeByCategory(JokeCategory content) {
        Map<UUID, Joke> jokeList = jokeRepository.findByCategory(content);
        return jokeList;
    }

    @Override
    public UUID addJoke(Joke newjoke) {
        return jokeRepository.save(newjoke);
    }

    @Override
    public boolean deleteJoke(UUID id) {
        return jokeRepository.delete(id);
    }

    @Override
    public boolean updateJoke(UUID id, Joke newjoke) {
        return jokeRepository.update(id, newjoke);
    }
}
