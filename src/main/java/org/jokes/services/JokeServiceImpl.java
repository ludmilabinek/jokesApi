package org.jokes.services;

import org.jokes.model.*;
import org.jokes.repository.JokeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JokeServiceImpl implements JokeService{

    private final JokeRepository jokeRepository;

    public JokeServiceImpl(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    @Override
    public List<Joke> getJokes() {
        return jokeRepository.findAll();
    }

    @Override
    public List<Joke> getJokesPaging(int limit, int offset) {
        return jokeRepository.findPaging(limit, offset);
    }

    @Override
    public Optional<Joke> getJokeById(UUID id) {
        return Optional.ofNullable(jokeRepository.findById(id));
    }

    @Override
    public List<Joke> getSearchJoke(String content, String type, String category, String[] exluded, String[] included) {
        return jokeRepository.searchJoke(content,type, category, exluded, included);
    }

    @Override
    public UUID addJoke(JokeDTO newjoke) {
        return jokeRepository.save(JokeMapper.fromDTO(newjoke));
    }

    @Override
    public boolean deleteJoke(UUID id) {
        return jokeRepository.delete(id);
    }

    @Override
    public boolean updateJoke(UUID id, JokeDTO newjoke) {
        return jokeRepository.update(id, JokeMapper.fromDTO(newjoke));
    }
}