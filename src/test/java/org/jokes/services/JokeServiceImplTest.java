package org.jokes.services;

import org.jokes.model.Joke;
import org.jokes.model.JokeCategory;
import org.jokes.model.JokeType;
import org.jokes.repository.JokeRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JokeServiceImplTest {

    @Mock
    private JokeRepositoryImpl jokeRepository;

    @InjectMocks
    private JokeServiceImpl jokeService;

    private static Map<UUID, Joke> jokeList;

    @BeforeAll
    public static void setUp(){
        jokeList = new HashMap<>();
    }

    @AfterEach
    public void tearDown(){
        jokeList.clear();
    }

    private void setData() {
        Joke newjoke = new Joke();
        newjoke.setCategory(JokeCategory.MISC);
        newjoke.setType(JokeType.SINGLE);
        newjoke.setJoke("I visited my friend at his new house. He told me to make myself at home. So I threw him out. I hate having visitors.");
        jokeList.put(newjoke.getId(), newjoke);

        Joke newjoke2 = new Joke();
        newjoke2.setCategory(JokeCategory.PROGRAMMING);
        newjoke2.setType(JokeType.SINGLE);
        newjoke2.setJoke("The six stages of debugging:\\n1. That can't happen.\\n2. That doesn't happen on my machine.\\n3. That shouldn't happen.\\n4. Why does that happen?\\n5. Oh, I see.\\n6. How did that ever work?");
        jokeList.put(newjoke2.getId(), newjoke2);
    }

    @DisplayName("Add joke test")
    @Test
    public void testAddJoke() {

        //Given
        when(jokeService.addJoke(any(Joke.class))).then(invocation -> {
            Joke tempJoke = invocation.getArgument(0, Joke.class);
            jokeList.put(tempJoke.getId(), tempJoke);
            return tempJoke.getId();
        });
        when(jokeService.getJokes()).thenReturn(jokeList);

        Joke newjoke = new Joke();
        newjoke.setCategory(JokeCategory.PROGRAMMING);
        newjoke.setType(JokeType.SINGLE);
        newjoke.setJoke("This is a test");

        //when
        jokeService.addJoke(newjoke);
        Map<UUID, Joke> jokeListInTest = jokeService.getJokes();

        //assert
        assertEquals(1, jokeListInTest.size());
        assertEquals(jokeListInTest.get(newjoke.getId()), newjoke);
    }

    @DisplayName("get jokes test")
    @Test
    public void testGetJokes() {
        this.setData();
        //Given
        when(jokeService.getJokes()).thenReturn(jokeList);

        //when
        Map<UUID, Joke> jokeListInTest = jokeService.getJokes();

        //assert
        assertEquals(2, jokeListInTest.size());
    }

    @DisplayName("get joke by id test")
    @Test
    public void testGetJokeById() {
        this.setData();
        //Given
        when(jokeService.getJokeById(any(UUID.class))).then(invocation -> {
            UUID id = invocation.getArgument(0, UUID.class);
            if(jokeList.containsKey(id)) {
                return jokeList.get(id);
            }
            return null;
        });

        //when
        Optional<Joke> resultJoke = jokeService.getJokeById(jokeList.entrySet().iterator().next().getKey());
        Optional<Joke> resultJoke2 = jokeService.getJokeById(jokeList.entrySet().iterator().next().getKey());
        Optional<Joke> resultJoke3= jokeService.getJokeById(UUID.randomUUID());

        //assert
        assertTrue(resultJoke.isPresent());
        assertTrue(resultJoke2.isPresent());
        assertTrue(resultJoke3.isEmpty());
    }

    @DisplayName("get joke by type test")
    @Test
    public void testGetJokeByType() {
        this.setData();
        //Given
        when(jokeService.getJokeByType(any(JokeType.class))).then(invocation -> {
            JokeType content = invocation.getArgument(0, JokeType.class);
            Map<UUID, Joke> resultList = jokeList.entrySet().stream()
                    .filter(map -> map.getValue().getType().equals(content))
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

            return resultList;
        });

        //when
        Map<UUID, Joke> resultJoke = jokeService.getJokeByType(JokeType.SINGLE);
        Map<UUID, Joke> resultJoke2 = jokeService.getJokeByType(JokeType.TWOPART);

        //assert
        assertFalse(resultJoke.isEmpty());
        assertTrue(resultJoke2.isEmpty());
        assertEquals(2, resultJoke.size());
    }

    @DisplayName("get joke by category test")
    @Test
    public void testGetJokeByCategory() {
        this.setData();
        //Given
        when(jokeService.getJokeByCategory(any(JokeCategory.class))).then(invocation -> {
            JokeCategory content = invocation.getArgument(0, JokeCategory.class);
            Map<UUID, Joke> resultList = jokeList.entrySet().stream()
                    .filter(map -> map.getValue().getCategory().equals(content))
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

            return resultList;
        });

        //when
        Map<UUID, Joke> resultJoke = jokeService.getJokeByCategory(JokeCategory.PROGRAMMING);
        Map<UUID, Joke> resultJoke2 = jokeService.getJokeByCategory(JokeCategory.CHRISTMAS);

        //assert
        assertFalse(resultJoke.isEmpty());
        assertTrue(resultJoke2.isEmpty());
        assertEquals(1, resultJoke.size());
    }

    @DisplayName("search joke by content test")
    @Test
    public void testGetJokeByContent() {
        this.setData();
        //Given
        when(jokeService.getJokeByContent(any(String.class))).then(invocation -> {
            String content = invocation.getArgument(0, String.class);
            Map<UUID, Joke> resultList = jokeList.entrySet().stream()
                    .filter(map -> map.getValue().getJoke().contains(content))
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

            return resultList;
        });

        //when
        Map<UUID, Joke> resultJoke = jokeService.getJokeByContent("my");
        Map<UUID, Joke> resultJoke2 = jokeService.getJokeByContent("visit");
        Map<UUID, Joke> resultJoke3 = jokeService.getJokeByContent("Lorem ipsum");

        //assert
        assertFalse(resultJoke.isEmpty());
        assertFalse(resultJoke2.isEmpty());
        assertTrue(resultJoke3.isEmpty());
        assertEquals(2, resultJoke.size());
        assertEquals(1, resultJoke2.size());
    }

    @DisplayName("delete joke test")
    @Test
    public void testDeleteJoke() {
        this.setData();
        //Given
        when(jokeService.deleteJoke(any(UUID.class))).then(invocation -> {
            UUID id = invocation.getArgument(0, UUID.class);
            if(jokeList.containsKey(id)) {
                if(jokeList.remove(id)!=null) {
                    return true;
                }
            }
            return false;
        });

        //when
        boolean status = jokeService.deleteJoke(jokeList.entrySet().iterator().next().getKey());
        boolean status2 = jokeService.deleteJoke(UUID.randomUUID());

        //assert
        assertTrue(status);
        assertFalse(status2);
    }

    @DisplayName("update joke test")
    @Test
   public void testUpdateJoke() {
        this.setData();
        //Given
        when(jokeService.updateJoke(any(UUID.class),any(Joke.class))).then(invocation -> {
            UUID id = invocation.getArgument(0, UUID.class);
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
        newjoke.setCategory(JokeCategory.SPOOKY);
        newjoke.setType(JokeType.TWOPART);
        newjoke.setJoke("This is a test");

        //when
        boolean status = jokeService.updateJoke(jokeList.entrySet().iterator().next().getKey(), newjoke);
        boolean status2 = jokeService.updateJoke(UUID.randomUUID(), newjoke);

        //assert
        assertTrue(status);
        assertFalse(status2);
        assertTrue(jokeList.containsValue(newjoke));

    }
}
