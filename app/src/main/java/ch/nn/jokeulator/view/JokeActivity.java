package ch.nn.jokeulator.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeApi;
import ch.nn.jokeulator.service.JokeService;

public class JokeActivity extends AppCompatActivity {

    private JokeApi jokeApi;
    private JokeService jokeService;
    private TextView jokeCategory, jokeText;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            JokeService.JokeBinder binder = (JokeService.JokeBinder) service;
            jokeService = binder.getService();
            initJoke();
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

    private void initComponents() {
        jokeCategory = findViewById(R.id.joke_category);
        jokeText = findViewById(R.id.joke_text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    private void initExtras() {
        this.jokeApi = new JokeApi(getIntent().getStringExtra("jokeApi"), getIntent().getStringExtra("jokeApiName"), getIntent().getStringExtra("jokeApiJsonLabel"));
        jokeCategory.setText(jokeApi.name);
    }

    private void initJoke() {
        findViewById(R.id.joke_text);
        this.jokeText.setText(this.jokeService.loadJoke(this.jokeApi));
    }

    public void skipJoke(View view) {

    }

    public void changeJokeCategory(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}