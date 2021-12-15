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
        reader.beginArray();
        reader.beginObject();
        reader.beginArray();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(this.jsonLabel)) {
                return reader.nextString() + "\n " + reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        return JokeService.DEFAULT_JOKE_TEXT;
    }
}
