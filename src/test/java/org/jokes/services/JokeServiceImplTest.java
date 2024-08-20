package org.jokes.services;

import org.jokes.model.*;
import org.jokes.repository.JokeRepositoryImpl;
import org.jokes.utils.JokeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JokeServiceImplTest {

//    @InjectMocks
    private final JokeRepositoryImpl jokeRepository = new JokeRepositoryImpl();

//    @InjectMocks
    private final JokeServiceImpl jokeService = new JokeServiceImpl(jokeRepository);


    private void setData() {
        Joke newjoke = new Joke();
        newjoke.setCategory(JokeCategory.MISC);
        newjoke.setType(JokeType.SINGLE);
        newjoke.setJoke("I visited my friend at his new house. He told me to make myself at home. So I threw him out. I hate having visitors.");
        newjoke.setJokeFlagsMap(JokeUtils.createJokeFlagsMap(true,false,false,false, false, false));
        jokeRepository.save(newjoke);

        Joke newjoke2 = new Joke();
        newjoke2.setCategory(JokeCategory.PROGRAMMING);
        newjoke2.setType(JokeType.SINGLE);
        newjoke2.setJoke("The six stages of debugging:\\n1. That can't happen.\\n2. That doesn't happen on my machine.\\n3. That shouldn't happen.\\n4. Why does that happen?\\n5. Oh, I see.\\n6. How did that ever work?");
        newjoke2.setJokeFlagsMap(JokeUtils.createJokeFlagsMap(true,false,true,false, false, false));
        jokeRepository.save(newjoke2);
    }

    private void setBigData() {
        String jsonData = JokeUtils.returnSampleData();

        JSONArray jsonArray = new JSONArray(jsonData);
        JSONObject jObject, jFlags;

        for (int i = 0; i < jsonArray.length(); i++) {
            jObject = jsonArray.getJSONObject(i);
            String secondPart = "";

            if (jObject.opt("secondPart") != null) {
                secondPart = jObject.get("secondPart").toString();
            }

            jFlags = new JSONObject(jObject.get("flags").toString());

            Joke newjoke = new Joke();
            newjoke.setJoke(jObject.get("joke").toString());
            newjoke.setSecondPart(secondPart);
            newjoke.setType(JokeType.valueOf(jObject.get("type").toString()));
            newjoke.setCategory(JokeCategory.valueOf(jObject.get("category").toString()));
            newjoke.setJokeFlagsMap(JokeUtils.createJokeFlagsMap((Boolean) jFlags.get("nsfw"), (Boolean) jFlags.get("religious"), (Boolean) jFlags.get("political"), (Boolean) jFlags.get("sexist"), (Boolean) jFlags.get("religious"), (Boolean) jFlags.get("explicit")));
            jokeRepository.save(newjoke);
        }
    }

    @DisplayName("Add joke test")
    @Test
    public void testAddJoke() {
        //Given
        JokeDTO newjokeDTO = new JokeDTO();
        newjokeDTO.setCategory(JokeCategory.PROGRAMMING);
        newjokeDTO.setFlags(JokeUtils.createJokeFlagsMap(true,false,false,false, false, false));
        newjokeDTO.setJoke("This is a test");

        //when
        jokeService.addJoke(newjokeDTO);
        List<Joke> jokeListInTest = jokeService.getJokes();

        //assert
        assertEquals(1, jokeListInTest.size());
        assertEquals(jokeListInTest.getFirst().getCategory(), newjokeDTO.getCategory());
    }

    @DisplayName("get jokes test")
    @Test
    public void testGetJokes() {
        //Given
        this.setData();

        //when
        List<Joke> jokeListInTest = jokeService.getJokes();

        //assert
        assertEquals(2, jokeListInTest.size());
    }

    @DisplayName("get joke by id test")
    @Test
    public void testGetJokeById() {
        // given
        this.setData();
        List<Joke> jokeList = jokeService.getJokes();

        //when
        Optional<Joke> resultJoke = jokeService.getJokeById(jokeList.getFirst().getId());
        Optional<Joke> resultJoke2 = jokeService.getJokeById(jokeList.getLast().getId());
        Optional<Joke> resultJoke3= jokeService.getJokeById(UUID.randomUUID());

        //assert
        assertTrue(resultJoke.isPresent());
        assertTrue(resultJoke2.isPresent());
        assertTrue(resultJoke3.isEmpty());
    }

    @DisplayName("search joke test")
    @Test
    public void testSearchJoke() {
        //Given
        this.setBigData();

        //when
        List<Joke> resultJoke = jokeService.getSearchJoke(null, null, null, null, null);
        List<Joke> resultJoke1 = jokeService.getSearchJoke("and", JokeType.TWOPART.toString(), null, null, null);
        System.out.println(resultJoke.size());
        List<Joke> resultJoke2 = jokeService.getSearchJoke(null, JokeType.TWOPART.toString(), JokeCategory.MISC.name(), null, null);
        String[] arrayExcluded = {JokeFlags.EXPLICIT.toString(), JokeFlags.SEXIST.toString()};
        String[] arrayIncluded = {JokeFlags.EXPLICIT.toString()};
        List<Joke> resultJoke3 = jokeService.getSearchJoke(null, JokeType.TWOPART.toString(), null, arrayExcluded, null);
        List<Joke> resultJoke4 = jokeService.getSearchJoke(null, null, null, null, arrayIncluded);
        List<Joke> resultJoke5 = jokeService.getSearchJoke(null, JokeType.SINGLE.toString(), null, null, arrayIncluded);


        //assert
        assertFalse(resultJoke.isEmpty());
        assertEquals(10, resultJoke.size());
        assertFalse(resultJoke1.isEmpty());
        assertEquals(2, resultJoke1.size());
        assertFalse(resultJoke2.isEmpty());
        assertEquals(2, resultJoke2.size());
        assertFalse(resultJoke3.isEmpty());
        assertEquals(7, resultJoke3.size());
        assertFalse(resultJoke4.isEmpty());
        assertEquals(1, resultJoke4.size());
        assertNull(resultJoke5);
    }


    @DisplayName("delete joke test")
    @Test
    public void testDeleteJoke() {
        //Given
        this.setData();
        List<Joke> jokeList = jokeService.getJokes();

        //when
        boolean status = jokeService.deleteJoke(jokeList.getFirst().getId());
        boolean status2 = jokeService.deleteJoke(UUID.randomUUID());

        //assert
        assertTrue(status);
        assertFalse(status2);
        assertEquals(jokeService.getJokes().size(), 1);
    }

    @DisplayName("update joke test")
    @Test
   public void testUpdateJoke() {
        //Given
        this.setData();

        JokeDTO newjoke = new JokeDTO();
        newjoke.setCategory(JokeCategory.SPOOKY);
        newjoke.setJoke("This is a test");
        newjoke.setSecondPart("This is a test");

        List<Joke> jokeList = jokeService.getJokes();

        //when
        UUID jokeId = jokeList.getFirst().getId();
        boolean status = jokeService.updateJoke(jokeId, newjoke);
        boolean status2 = jokeService.updateJoke(UUID.randomUUID(), newjoke);

        //assert
        assertTrue(status);
        assertFalse(status2);
        Optional<Joke> updatedJoke = jokeService.getJokeById(jokeId);

        assertEquals(updatedJoke.get().getCategory(), newjoke.getCategory());
    }

    @DisplayName("pagging test")
    @Test
    public void testPagging() {
        //Given
        this.setBigData();
        List<Joke> allJokeList = jokeService.getJokes();

        //when
        List<Joke> jokeList = jokeService.getJokesPaging(10, 0);
        List<Joke> jokeList1 = jokeService.getJokesPaging(10, 2);
        List<Joke> jokeList2 = jokeService.getJokesPaging(2, 9);
        List<Joke> jokeList3 = jokeService.getJokesPaging(1, 0);
        List<Joke> jokeList4 = jokeService.getJokesPaging(1, 1);
        List<Joke> jokeList5 = jokeService.getJokesPaging(1, 2);

        //assert
        assertFalse(jokeList.isEmpty());
        assertEquals(10, jokeList.size());

        assertFalse(jokeList1.isEmpty());
        assertEquals(8, jokeList1.size());

        assertFalse(jokeList2.isEmpty());
        assertEquals(1, jokeList2.size());

        assertEquals(jokeList3.getFirst(), allJokeList.getFirst());
        assertEquals(jokeList4.getFirst(), allJokeList.get(1));
        assertEquals(jokeList5.getFirst(), allJokeList.get(2));
    }
}