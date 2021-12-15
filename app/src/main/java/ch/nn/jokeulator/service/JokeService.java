package ch.nn.jokeulator.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.JsonReader;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableFloat;

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

public class JokeService extends Service implements SensorEventListener {

    public static final String CHARSET_NAME = "UTF-8";
    public static final String DEFAULT_JOKE_TEXT = "Could not load joke :(";
    private final IBinder binder = new JokeBinder();


    private final static int SAMPLING_RATE = 10000000; // in microseconds
    public ObservableFloat laughRate = new ObservableFloat(0);

    private float last_x, last_y, last_z;
    private long lastUpdate;


    private void initializeSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SAMPLING_RATE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        calculateLaughRate(event.values);
    }

    private void calculateLaughRate(float[] values) {
        long curTime = System.currentTimeMillis();
        long diffTime = (curTime - lastUpdate);

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
        if ((curTime - lastUpdate) > 1500) {
            this.laughRate.set(speed / 100);
            lastUpdate = curTime;
        } else if (this.laughRate.get() < speed) {
            this.laughRate.set(speed / 100);
        }

        last_x = x;
        last_y = y;
        last_z = z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not needed
    }


    public class JokeBinder extends Binder {
        public JokeService getService() {
            return JokeService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initializeSensor();
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
                reader.setLenient(true);
                joke = jokeApi.getJokeJson(reader);
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
