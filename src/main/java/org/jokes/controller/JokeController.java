package org.jokes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jokes.model.*;
import org.jokes.services.JokeServiceImpl;
import org.jokes.utils.JokeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api")
public class JokeController {

    private final JokeServiceImpl jokeService;

    public JokeController(JokeServiceImpl jokeService) {
        this.jokeService = jokeService;

        try {
            this.addTestData();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/jokes")
    public ResponseEntity<Collection<Joke>> getAllJokes() {
        List<Joke> jokeList = jokeService.getJokes();
        if (jokeList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(jokeList);
        }
    }

    @GetMapping("/jokes_paging")
    @ResponseBody
    public ResponseEntity<Collection<Joke>> getAllJokesPaging(@RequestParam(value = "limit", required = false, defaultValue = "10") int limit, @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        List<Joke> jokeList = jokeService.getJokesPaging(limit, offset);
        if (jokeList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(jokeList);
        }
    }

    @GetMapping("/jokes/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable("id") UUID id) {
        Optional<Joke> joke = jokeService.getJokeById(id);

        if(joke.isPresent()) {
            return ResponseEntity.ok(joke.get());
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/search_joke")
    @ResponseBody
    public ResponseEntity<Collection<Joke>> searchJokes(@RequestParam(value = "content", required = false, defaultValue = "") String content,
                                                        @RequestParam(value = "type", required = false, defaultValue = "") String type,
                                                        @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                                        @RequestParam(value = "excludedflags", required = false, defaultValue = "") String[] exluded,
                                                        @RequestParam(value = "includedflags", required = false, defaultValue = "") String[] included) {

        List<Joke> jokeList = jokeService.getSearchJoke(content, type, category, exluded, included);

        if (jokeList==null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(jokeList);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addJoke(@RequestBody JokeDTO newjoke) {
        UUID jokeId = jokeService.addJoke(newjoke);
        URI uri = URI.create("/api/jokes/" + jokeId);
        return ResponseEntity.created(uri).body("created new joke with id: " + jokeId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJoke(@PathVariable("id") UUID id) {
        boolean status = jokeService.deleteJoke(id);
        if(status) {
            return ResponseEntity.ok("deleted joke with id: " + id.toString());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJoke(@RequestBody JokeDTO newjoke, @PathVariable("id") UUID id) {
        boolean status = jokeService.updateJoke(id, newjoke);
        if(status) {
            return ResponseEntity.ok().body("updated joke with id: " + id.toString());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }



    private void addTestData() throws JsonProcessingException {
        String jsonData = JokeUtils.returnSampleData();
        JSONArray jsonArray = new JSONArray(jsonData);
        JSONObject jObject, jFlags;

        for (int i = 0; i < jsonArray.length(); i++) {
            jObject = jsonArray.getJSONObject(i);
            String secondPart = "";

            if(jObject.opt("secondPart")!=null) {
                secondPart = jObject.get("secondPart").toString();
            }

            jFlags = new JSONObject(jObject.get("flags").toString());

            JokeDTO jokeDTO = new JokeDTO();
            jokeDTO.setJoke(jObject.get("joke").toString());
            jokeDTO.setSecondPart(secondPart);
            jokeDTO.setCategory(JokeCategory.valueOf(jObject.get("category").toString()));
            jokeDTO.setFlags(JokeUtils. createJokeFlagsMap((Boolean) jFlags.get("nsfw"),(Boolean) jFlags.get("religious"),(Boolean) jFlags.get("political"), (Boolean) jFlags.get("sexist"), (Boolean) jFlags.get("religious"), (Boolean) jFlags.get("explicit")));
            jokeService.addJoke(jokeDTO);
        }
    }
}