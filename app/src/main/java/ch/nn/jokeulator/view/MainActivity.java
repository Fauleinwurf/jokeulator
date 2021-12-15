package ch.nn.jokeulator.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import ch.nn.jokeulator.R;
import ch.nn.jokeulator.model.JokeApiEnum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.joke_container);

        int index = 0;
        for (JokeApiEnum jokeApiEnum : JokeApiEnum.values()) {
            Button button = new Button(this);
            button.setId(index++);
            button.setOnClickListener((view) -> {
                startJokeActivity(jokeApiEnum);
            });
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(0, 0, 0, 5);
            button.setLayoutParams(layout);
            button.setText(jokeApiEnum.api.name);
            button.setBackgroundColor(getResources().getColor(R.color.purple_200));

            linearLayout.addView(button);
        }
    }

    private void startJokeActivity(JokeApiEnum jokeApiEnum) {
        Intent intent = new Intent(getBaseContext(), JokeActivity.class);
        intent.putExtra("jokeApiEnum", jokeApiEnum.name());
        startActivity(intent);
    }
}