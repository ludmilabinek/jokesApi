package org.jokes.services;

import org.jokes.model.Joke;
import org.jokes.repository.JokeRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JokeServiceImplTest {

    @Mock
    private JokeRepositoryImpl jokeRepository;

    @InjectMocks
    private JokeServiceImpl jokeService;

    private static HashMap<Integer, Joke> jokeList;

    @BeforeEach
    public void setUp(){
        jokeList = new HashMap<>();

        Joke newjoke = new Joke();
        newjoke.setId(1);
        newjoke.setCategory("it");
        newjoke.setType("funy");
        newjoke.setJoke("This is a test");
        jokeList.put(1, newjoke);

        Joke newjoke2 = new Joke();
        newjoke2.setId(2);
        newjoke2.setCategory("it");
        newjoke2.setType("very funy");
        newjoke2.setJoke("This is a test 2");
        jokeList.put(2, newjoke);
    }

    @AfterEach
    public void tearDown(){
        jokeList.clear();
    }

    @DisplayName("Add joke test")
    @Test
    public void testAddJoke() {
        jokeList.clear();
        //Given
        when(jokeService.addJoke(any(Joke.class))).then(invocation -> {
            Joke tempJoke = invocation.getArgument(0, Joke.class);
            jokeList.put(tempJoke.getId(), tempJoke);
            return tempJoke;
        });
        when(jokeService.getJokes()).thenReturn(jokeList);

        Joke newjoke = new Joke();
        newjoke.setId(1);
        newjoke.setCategory("it");
        newjoke.setType("funy");
        newjoke.setJoke("This is a test");

        //when
        jokeService.addJoke(newjoke);
        HashMap<Integer, Joke> jokeListInTest = jokeService.getJokes();

        //assert
        assertTrue(jokeListInTest.size()==1);
        assertTrue(jokeListInTest.get(1).equals(newjoke));
    }

    @DisplayName("get jokes test")
    @Test
    public void testGetJokes() {
        //Given
        when(jokeService.getJokes()).thenReturn(jokeList);

        //when
        HashMap<Integer, Joke> jokeListInTest = jokeService.getJokes();

        //assert
        assertTrue(jokeListInTest.size()==2);
    }

    @DisplayName("get joke by id test")
    @Test
    public void testGetJokeById() {
        //Given
        when(jokeService.getJokeById(any(Integer.class))).then(invocation -> {
            Integer id = invocation.getArgument(0, Integer.class);
            if(jokeList.containsKey(id)) {
                Joke tempJoke = jokeList.get(id);
                return tempJoke;
            }
            return null;
        });

        //when
        Optional<Joke> resultjoke = jokeService.getJokeById(1);
        Optional<Joke> resultjoke2 = jokeService.getJokeById(2);
        Optional<Joke> resultjoke3= jokeService.getJokeById(3);

        //assert
        assertTrue(resultjoke.isPresent());
        assertTrue(resultjoke2.isPresent());
        assertTrue(resultjoke3.isEmpty());
    }

    @DisplayName("delete joke test")
    @Test
    public void testDeleteJoke() {
        //Given
        when(jokeService.deleteJoke(any(Integer.class))).then(invocation -> {
            Integer id = invocation.getArgument(0, Integer.class);
            if(jokeList.containsKey(id)) {
                if(jokeList.remove(id)!=null) {
                    return true;
                }
            }
            return false;
        });

        //when
        boolean status = jokeService.deleteJoke(1);
        boolean status2 = jokeService.deleteJoke(3);

        //assert
        assertTrue(status);
        assertFalse(status2);
    }

    @DisplayName("update joke test")
    @Test
   public void testUpdateJoke() {
        //Given
        when(jokeService.updateJoke(any(Integer.class),any(Joke.class))).then(invocation -> {
            Integer id = invocation.getArgument(0, Integer.class);
            Joke newJoke = invocation.getArgument(1, Joke.class);
            if(jokeList.containsKey(id)) {
                jokeList.replace(id, newJoke);
                if(jokeList.get(id).equals(newJoke)) {
                    return true;
                }
            }
            return false;
        });

        Joke newjoke = new Joke();
        newjoke.setId(1);
        newjoke.setCategory("it");
        newjoke.setType("very funy");
        newjoke.setJoke("This is a test");

        //when
        boolean status = jokeService.updateJoke(1, newjoke);
        boolean status2 = jokeService.updateJoke(3, newjoke);

        //assert
        assertTrue(status);
        assertFalse(status2);
        assertTrue(jokeList.containsValue(newjoke));

    }
}
