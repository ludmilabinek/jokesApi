package org.jokes.services;

import org.jokes.model.Joke;
import org.jokes.repository.JokeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import java.util.Optional;

@Service
public class JokeServiceImpl implements JokeService{

    private final JokeRepository jokeRepository;

    public JokeServiceImpl(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public HashMap<Integer, Joke> getJokes() {
        HashMap<Integer, Joke> jokeList = jokeRepository.findAll();
        return jokeList;
    }

    @Override
    public Optional<Joke> getJokeById(int id) {
        Optional<Joke> joke = Optional.ofNullable(jokeRepository.findById(id));
        return joke;
    }

    @Override
    public Joke addJoke(Joke newjoke) {
        return jokeRepository.save(newjoke);
    }

    @Override
    public boolean deleteJoke(int id) {
        return jokeRepository.delete(id);
    }

    @Override
    public boolean updateJoke(int id, Joke newjoke) {
        return jokeRepository.update(id, newjoke);
    }
}
