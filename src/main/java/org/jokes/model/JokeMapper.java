package org.jokes.model;

public final class JokeMapper {

    private JokeMapper() {
        // empty
    }

    public static Joke fromDTO(JokeDTO jDTO){
        Joke joke = new Joke();
        joke.setJoke(jDTO.getJoke());
        if(jDTO.getSecondPart() == null || jDTO.getSecondPart().isEmpty()) {
            joke.setType(JokeType.SINGLE);
        } else {
            joke.setSecondPart(jDTO.getSecondPart());
            joke.setType(JokeType.TWOPART);
        }
        joke.setCategory(jDTO.getCategory());

        joke.setJokeFlagsMap(jDTO.getFlags());
        return joke;
    }

}