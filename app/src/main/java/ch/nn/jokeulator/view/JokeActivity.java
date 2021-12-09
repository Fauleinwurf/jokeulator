package ch.nn.jokeulator.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeApi;

public class JokeActivity extends AppCompatActivity {

    private JokeApi jokeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        this.initExtras();

        TextView categoryTitle = findViewById(R.id.joke_category);
        categoryTitle.setText(jokeApi.name);
    }

    private void initExtras() {
        this.jokeApi = new JokeApi(getIntent().getStringExtra("jokeApi"), getIntent().getStringExtra("jokeApiName"));
    }

    public void skipJoke(View view) {

    }

    public void changeJokeCategory(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}