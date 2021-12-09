package ch.nn.jokeulator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivityFirstButton(View view) {
        startJokeActivity(JokeType.DAD_JOKES);
    }

    public void startActivitySecondButton(View view) {
        startJokeActivity(JokeType.CHUCK_NORRIS_JOKES);
    }

    private void startJokeActivity(String jokeType) {
        Intent intent = new Intent(getBaseContext(), JokeActivity.class);
        intent.putExtra("jokeApi", jokeType);
        startActivity(intent);
    }
}