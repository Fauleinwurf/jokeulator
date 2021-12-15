package ch.nn.jokeulator.model;

public enum JokeApiEnum {
    CHUCK_NORRIS(new JokeApi("https://api.chucknorris.io/jokes/random/", "Chuck Norris jokes", "value")),
    DAD(new DadJokeApi("https://api.dadjokes.io/api/random/joke/", "Dad Jokes", "setup"));

    public final JokeApi api;

    JokeApiEnum(JokeApi jokeApi) {
        api = jokeApi;
    }
}
