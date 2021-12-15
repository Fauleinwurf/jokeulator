package ch.nn.jokeulator.model;

import android.util.JsonReader;

import java.io.IOException;

import ch.nn.jokeulator.service.JokeService;

public enum JokeApiEnum {
    CHUCK_NORRIS(new JokeApi("https://api.chucknorris.io/jokes/random/", "Chuck Norris jokes", "value")),
    DAD(new JokeApi("https://api.dadjokes.io/api/random/joke/", "Dad Jokes", "setup"){
        @Override
        public String getJokeJson(JsonReader reader) throws IOException {
            reader.beginObject();
            reader.nextName();
            reader.skipValue();
            reader.nextName();
            reader.beginArray();
            reader.beginObject();
            StringBuilder joke = new StringBuilder(JokeService.DEFAULT_JOKE_TEXT);
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals(this.jsonLabel)) {
                    joke.setLength(0);
                    joke.append(reader.nextString()).append("\n\n");
                    reader.nextName();
                    joke.append(reader.nextString()).append("\n\n");
                } else {
                    reader.skipValue();
                }
            }
            return joke.toString();
        }
    });

    public final JokeApi api;

    JokeApiEnum(JokeApi jokeApi) {
        api = jokeApi;
    }
}
