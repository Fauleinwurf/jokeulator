package ch.nn.jokeulator.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivityFirstButton(View view) {
        startJokeActivity(JokeApi.DAD_JOKES);
    }

    public void startActivitySecondButton(View view) {
        startJokeActivity(JokeApi.CHUCK_NORRIS_JOKES);
    }

    private void startJokeActivity(String jokeApi) {
        Intent intent = new Intent(getBaseContext(), JokeActivity.class);
        intent.putExtra("jokeApi", jokeApi);
        startActivity(intent);
    }
}