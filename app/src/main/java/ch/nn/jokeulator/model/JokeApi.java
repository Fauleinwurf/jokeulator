package ch.nn.jokeulator.model;

import android.util.JsonReader;

import java.io.IOException;

import ch.nn.jokeulator.service.JokeService;

public class JokeApi {
    public final String api;
    public final String name;
    public final String jsonLabel;

    public JokeApi(String api, String name, String jsonLabel) {
        this.api = api;
        this.name = name;
        this.jsonLabel = jsonLabel;
    }

    public String getJokeJson(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(this.jsonLabel)) {
                return reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        return JokeService.DEFAULT_JOKE_TEXT;
    }
}
