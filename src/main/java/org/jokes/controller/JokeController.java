package org.jokes.controller;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeType;
import org.jokes.services.JokeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class JokeController {

    @Autowired
    JokeServiceImpl jokeService;

    @GetMapping("/jokes")
    public ResponseEntity<Collection<Joke>> getAllJokes() {
        Map<UUID, Joke> jokeList = jokeService.getJokes();
        if (jokeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(jokeList.values(), HttpStatus.OK);
        }
    }

    @GetMapping("/jokes/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable("id") UUID id) {
        Optional<Joke> joke = jokeService.getJokeById(id);

        if(joke.isPresent()) {
            return new ResponseEntity<>(joke.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/jokes/content/{content}")
    public ResponseEntity<Collection<Joke>> getJokeByContent(@PathVariable("content") String content) {
        Map<UUID, Joke> jokeList = jokeService.getJokeByContent(content);

        if (jokeList==null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(jokeList.values(), HttpStatus.OK);
        }
    }

    @GetMapping("/jokes/type/{content}")
    public ResponseEntity<Collection<Joke>> getJokeByType(@PathVariable("content") JokeType content) {
        Map<UUID, Joke> jokeList = jokeService.getJokeByType(content);

        if (jokeList==null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(jokeList.values(), HttpStatus.OK);
        }
    }

    @GetMapping("/jokes/category/{content}")
    public ResponseEntity<Collection<Joke>> getJokeByCategory(@PathVariable("content") JokeCategory content) {
        Map<UUID, Joke> jokeList = jokeService.getJokeByCategory(content);

        if (jokeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(jokeList.values(), HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addJoke(@RequestBody Joke newjoke) {
        UUID jokeId = jokeService.addJoke(newjoke);
        return new ResponseEntity<>("created new joke with id: " + jokeId.toString(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Joke> deleteJoke(@PathVariable("id") UUID id) {
        boolean status = jokeService.deleteJoke(id);
        if(status) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Joke> updateJoke(@RequestBody Joke newjoke, @PathVariable("id") UUID id) {
        boolean status = jokeService.updateJoke(id, newjoke);
        if(status) {
            return new ResponseEntity<>(newjoke, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}