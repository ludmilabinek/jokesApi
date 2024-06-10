package org.jokes.repository;

import org.jokes.model.Joke;
import org.springframework.stereotype.Repository;


import java.util.HashMap;


@Repository
public class JokeRepositoryImpl implements JokeRepository{

    private HashMap<Integer, Joke> jokeList = new HashMap<>();

    @Override
    public HashMap<Integer, Joke> findAll() {
        return jokeList;
    }

    @Override
    public Joke findById(int id) {
        if(jokeList.containsKey(id)) {
            return  jokeList.get(id);
        }
        else {
            return null;
        }
    }

    @Override
    public Joke save(Joke joke) {
        if(jokeList.containsKey(joke.getId())) {
            return jokeList.get(joke.getId());
        }
        else {
            return jokeList.put(joke.getId(), joke);
        }
    }

    @Override
    public boolean delete(int id) {
        if(jokeList.containsKey(id)) {
            if(jokeList.remove(id)!=null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean update(int id, Joke newjoke) {
        if(jokeList.containsKey(id)) {
            jokeList.replace(id, newjoke);
            if(jokeList.get(id).equals(newjoke)) {
                return true;
            }
        }

        return false;
    }
}
