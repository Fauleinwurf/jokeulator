package ch.nn.jokeulator.model;

import android.util.JsonReader;

import java.io.IOException;

import ch.nn.jokeulator.service.JokeService;

public class DadJokeApi extends JokeApi {
    public DadJokeApi(String api, String name, String jasonLabel) {
        super(api,name,jasonLabel);
    }

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
                joke.append(reader.nextString()).append("\n\n");
                reader.nextName();
                joke.append(reader.nextString()).append("\n\n");
            } else {
                reader.skipValue();
            }
        }
        return joke.toString();
    }
}
