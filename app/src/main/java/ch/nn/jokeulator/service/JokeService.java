package ch.nn.jokeulator.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.JsonReader;

import androidx.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ch.nn.jokeulator.model.JokeApi;

public class JokeService extends Service {

    private final IBinder binder = new JokeBinder();
    public static final String CHARSET_NAME = "UTF-8";
    public static final String DEFAULT_JOKE_TEXT = "Could not load joke :(";


    public class JokeBinder extends Binder {
        public JokeService getService() {
            return JokeService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public String loadJoke(JokeApi jokeApi) {
        Future<String> jokeFuture = Executors.newSingleThreadExecutor().submit(() -> {
            String joke = DEFAULT_JOKE_TEXT;
            try {
                URL url = new URL(jokeApi.api);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream, CHARSET_NAME));
                reader.beginObject();
                while(reader.hasNext()) {
                    String name = reader.nextName();
                    if(name.equals(jokeApi.jsonLabel)) {
                        joke = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return joke;
        });

        try {
            return jokeFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return DEFAULT_JOKE_TEXT;
    }
}
