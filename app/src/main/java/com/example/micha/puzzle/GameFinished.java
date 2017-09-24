package com.example.micha.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/*
    Activity called when game is finished.
    It asks user for nickname and save score in ranking.
 */

public class GameFinished extends Activity {

    public void saveScore(View v) {
        int size = this.getIntent().getIntExtra("size", 4);
        Ranking rank = new Ranking(this, String.valueOf(size));
        TextView nick = (TextView) findViewById(R.id.name);

        String time = this.getIntent().getStringExtra("score");

        rank.addNewScore(nick.getText().toString(), time);
        Intent main = new Intent(this, Core.class);
        startActivity(main);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
    }
}
