package ch.nn.jokeulator.model;

public class JokeApi {
    public final String api;
    public final String name;
    public final String jsonLabel;

    public JokeApi(String api, String name, String jsonLabel) {
        this.api = api;
        this.name = name;
        this.jsonLabel = jsonLabel;
    }
}
