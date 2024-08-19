package org.jokes.utils;

import org.jokes.model.JokeFlags;

import java.util.EnumMap;
import java.util.Map;

public final class JokeUtils {

    private JokeUtils() {
        // empty
    }

    public static Map<JokeFlags, Boolean> createJokeFlagsMap(Boolean nsfw, Boolean racist, Boolean political, Boolean sexist, Boolean religious, Boolean explicit) {
        Map<JokeFlags, Boolean> jokeFlagsMap = new EnumMap<>(JokeFlags.class);
        jokeFlagsMap.put(JokeFlags.NSFW, nsfw);
        jokeFlagsMap.put(JokeFlags.RACIST, racist);
        jokeFlagsMap.put(JokeFlags.POLITICAL, political);
        jokeFlagsMap.put(JokeFlags.SEXIST, sexist);
        jokeFlagsMap.put(JokeFlags.RELIGIOUS, religious);
        jokeFlagsMap.put(JokeFlags.EXPLICIT, explicit);
        return jokeFlagsMap;
    }

    public static String returnSampleData() {
        return "[{\"category\":\"PROGRAMMING\", \"type\":\"TWOPART\",\"joke\":\"What is the best prefix for global variables?\",\"secondPart\":\"pusto\",\"flags\":{\"nsfw\":false,\"religious\":false, \"political\":false,\"racist\":false,\"sexist\":false,\"explicit\":false}},{\"category\": \"PUN\",\"type\": \"TWOPART\",\"joke\": \"Did you hear about the claustrophobic astronaut?\",\"secondPart\": \"He just needed a little space.\",\"flags\": {\"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": false, \"explicit\": false}},{\"category\": \"CHRISTMAS\",\"type\": \"TWOPART\",\"joke\": \"Who hides in the bakery at Christmas?\",\"secondPart\": \"A mince spy!\",\"flags\": {\"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": false, \"explicit\": false}},{\"category\": \"PROGRAMMING\",\"type\": \"TWOPART\",\"joke\": \"Why do programmers prefer using the dark mode?\",\"secondPart\": \"Because light attracts bugs.\",\"flags\": { \"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": false, \"explicit\": false}},{\"category\": \"PROGRAMMING\",\"type\": \"TWOPART\",\"joke\": \"Why do Java programmers hate communism?\",\"secondPart\": \"They don't want to live in a classless society.\",\"flags\": {\"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": false, \"explicit\": false}},{\"category\": \"MISC\",\"type\": \"TWOPART\",\"joke\": \"Arguing with a woman is like reading a software's license agreement.\",\"secondPart\": \"In the end you ignore everything and click 'I agree'.\",\"flags\": { \"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": true, \"explicit\": false}},{\"category\": \"MISC\",\"type\": \"SINGLE\",\"joke\": \"Relationship Status: just tried to reach for my dog's paw and he pulled it away so I pretended I was reaching for the remote.\",\"flags\": { \"nsfw\": false, \"religious\": false, \"racist\": false, \"sexist\": false, \"political\": false, \"explicit\": false}},{\"category\": \"DARK\",\"type\": \"TWOPART\",\"joke\": \"How do you keep black people out of your back yard?\",\"secondPart\": \"Hang one in the front!\",\"flags\": {\"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": true, \"sexist\": false, \"explicit\": true}},{\"category\": \"PUN\",\"type\": \"TWOPART\",\"joke\": \"Why do front end developers eat lunch alone?\",\"secondPart\": \"Because they don't know how to join tables.\",\"flags\": {\"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": false, \"explicit\": false}},{\"category\": \"MISC\",\"type\": \"TWOPART\",\"joke\": \"My wife left me because I'm too insecure and paranoid.\",\"secondPart\": \"Oh wait, never mind. She was just getting the mail.\",\"flags\": {\"nsfw\": false, \"religious\": false, \"political\": false, \"racist\": false, \"sexist\": false, \"explicit\": false}}]";
    }
}