package org.jokes.repository;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeFlags;
import org.jokes.model.JokeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;

@Repository
public class JokeRepositoryImpl implements JokeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JokeRepositoryImpl.class);
    private final Map<UUID, Joke> jokeList = new HashMap<>();

    @Override
    public List<Joke> findAll() {
        return jokeList.entrySet().stream()
                .sorted(Comparator.comparing(j -> j.getValue().getCreateData()))
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public List<Joke> findPaging(int limit, int offset) {
        return jokeList.entrySet().stream()
                .sorted(Comparator.comparing(j -> j.getValue().getCreateData()))
                .skip(offset)
                .limit(limit)
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public Joke findById(UUID id) {
        return jokeList.getOrDefault(id, null);
    }

    @Override
    public List<Joke> searchJoke(String content, String type, String category, String[] exluded, String[] included) {

        List<Predicate<Joke>> allPredicates = new ArrayList<>();
        if (content != null && !content.isEmpty()) {
            allPredicates.add(p -> p.getJoke().contains(content) || (p.getSecondPart() != null && p.getSecondPart().contains(content)));
        }
        if (type != null && !type.isEmpty()) {
            allPredicates.add(p -> p.getType().equals(JokeType.valueOf(type.toUpperCase())));
        }
        if (category != null && !category.isEmpty()) {
            allPredicates.add(p -> p.getCategory().equals(JokeCategory.valueOf(category.toUpperCase())));
        }
        if (exluded != null) {
            for (String ex : exluded) {
                allPredicates.add(p -> !p.getJokeFlagsMap().entrySet().stream()
                        .filter(p1 -> p1.getKey().equals(JokeFlags.valueOf(ex.toUpperCase())) && p1.getValue().equals(false))
                        .toList().isEmpty()
                );
            }
        }
        if (included != null) {
            for (String in : included) {
                allPredicates.add(p -> !p.getJokeFlagsMap().entrySet().stream()
                        .filter(p1 -> p1.getKey().equals(JokeFlags.valueOf(in.toUpperCase())) && p1.getValue().equals(true))
                        .toList().isEmpty()
                );
            }
        }

        List<Joke> resultList = jokeList.values().stream()
                .filter(allPredicates.stream().reduce(j -> true, Predicate::and))
                .sorted(Comparator.comparing(Joke::getCreateData))
                .toList();

        if (!resultList.isEmpty()) {
            return resultList;
        } else {
            return null;
        }
    }

    @Override
    public UUID save(Joke joke) {
        Joke newJoke = new Joke();
        newJoke.setJoke(joke.getJoke());
        newJoke.setCategory(joke.getCategory());
        newJoke.setType(joke.getType());
        newJoke.setSecondPart(joke.getSecondPart());
        newJoke.setJokeFlagsMap(joke.getJokeFlagsMap());
        jokeList.put(newJoke.getId(), newJoke);
        return newJoke.getId();
    }

    @Override
    public boolean delete(UUID id) {
        if (jokeList.containsKey(id)) {
            return jokeList.remove(id) != null;
        }

        return false;
    }

    @Override
    public boolean update(UUID id, Joke newjoke) {
        if (jokeList.containsKey(id)) {
            jokeList.replace(id, newjoke);
            return jokeList.get(id).equals(newjoke);
        }

        return false;
    }
}