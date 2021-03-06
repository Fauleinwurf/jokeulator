package ch.nn.jokeulator.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeApi;
import ch.nn.jokeulator.model.JokeApiEnum;
import ch.nn.jokeulator.service.JokeService;

public class JokeActivity extends AppCompatActivity {

    private JokeApi jokeApi;
    private JokeService jokeService;
    private TextView jokeCategory, jokeText, laughRate;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            JokeService.JokeBinder binder = (JokeService.JokeBinder) service;
            jokeService = binder.getService();

            initJoke(false);
            initLaughRate();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent service = new Intent(this, JokeService.class);
        bindService(service, connection, Context.BIND_AUTO_CREATE);
        setContentView(R.layout.activity_joke);

        this.initComponents();
        this.initExtras();
    }

    private String loadJokeFromPreferences() {
        SharedPreferences prefs = getPrefs();
        return prefs.getString(buildJokeKey(), JokeService.DEFAULT_JOKE_TEXT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    private void initComponents() {
        jokeCategory = findViewById(R.id.joke_category);
        jokeText = findViewById(R.id.joke_text);
        laughRate = findViewById(R.id.laugh_rate_value);
    }

    private void initLaughRate() {
        this.jokeService.laughRate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                laughRate.setText(DECIMAL_FORMAT.format(jokeService.laughRate.get()));

            }
        });
    }

    private void initExtras() {
        this.jokeApi = JokeApiEnum.valueOf(getIntent().getStringExtra("jokeApiEnum")).api;
        jokeCategory.setText(jokeApi.name);
    }

    private void initJoke(boolean needsNewJoke) {
        String jokeText;

        if (getPrefs().contains(buildJokeKey()) && !needsNewJoke) {
            jokeText = loadJokeFromPreferences();
        } else {
            jokeText = this.jokeService.loadJoke(this.jokeApi);
            SharedPreferences.Editor editor = getPrefs().edit();
            editor.putString(buildJokeKey() , jokeText);
            editor.apply();
        }

        this.jokeText.setText(jokeText);
    }

    public void skipJoke(View view) {
        this.initJoke(true);
    }

    private String buildJokeKey(){
        return  "JOKE_TEXT_" + this.jokeApi.name.toUpperCase();
    }

    public void changeJokeCategory(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    private SharedPreferences getPrefs() {
        return getPreferences(Context.MODE_PRIVATE);
    }
}