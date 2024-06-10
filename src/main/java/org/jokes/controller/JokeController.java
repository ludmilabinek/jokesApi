package org.jokes.controller;

import org.jokes.model.Joke;
import org.jokes.services.JokeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class JokeController {

    @Autowired
    JokeServiceImpl jokeService;

    @GetMapping("/jokes")
    public ResponseEntity<HashMap<Integer, Joke>> getAllJokes() {
        System.out.println("tu wydrukujemy dowcipy");

        HashMap<Integer, Joke> jokeList = jokeService.getJokes();
        if (jokeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(jokeList, HttpStatus.OK);
        }
    }

    @GetMapping("/jokes/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable("id") int id) {
        System.out.println("wydruk konkretnego dowcipu");
        Optional<Joke> joke = jokeService.getJokeById(id);

        if(joke.isPresent()) {
            return new ResponseEntity<>(joke.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Joke> addJoke(@RequestBody Joke newjoke) {
        Joke joke = jokeService.addJoke(newjoke);
        return new ResponseEntity<>(joke, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Joke> deleteJoke(@PathVariable("id") int id) {
        System.out.println("usuwamy konkretny dowcip");
        boolean status = jokeService.deleteJoke(id);
        if(status) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Joke> updateJoke(@RequestBody Joke newjoke, @PathVariable("id") int id) {
        boolean status = jokeService.updateJoke(id, newjoke);
        if(status) {
            return new ResponseEntity<>(newjoke, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
