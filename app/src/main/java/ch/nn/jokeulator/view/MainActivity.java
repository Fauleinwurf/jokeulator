package ch.nn.jokeulator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeApi;

public class MainActivity extends AppCompatActivity {

    private final JokeApi[] jokeApis = new JokeApi[]{new JokeApi("https://api.chucknorris.io/jokes/random", "Chuck Norris jokes")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.joke_container);

        int index = 0;
        for (JokeApi api : this.jokeApis) {
            Button button = new Button(this);
            button.setId(index++);
            button.setOnClickListener((view) -> {
                startJokeActivity(api);
            });
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(0,0,0,5);
            button.setLayoutParams(layout);
            button.setText(api.name);

            linearLayout.addView(button);
        }
    }

    private void startJokeActivity(JokeApi api) {
        Intent intent = new Intent(getBaseContext(), JokeActivity.class);
        intent.putExtra("jokeApi", api.api);
        intent.putExtra("jokeApiName", api.name);
        startActivity(intent);
    }
}