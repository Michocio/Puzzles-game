package com.example.micha.puzzle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;

/*
    Displays ranking
 */
public class RankingTable extends AppCompatActivity {

    int prevButtonId = R.id.rank4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_table);

    }

    public void onClick(View v) {
        int type = 4;
        // Ranking of game with how many tiles?
        switch (v.getId()) {
            case R.id.rank4:
                type = 4;
                break;
            case R.id.rank9:
                type = 9;
                break;
            case R.id.rank16:
                type = 16;
                break;
            case R.id.rank25:
                type = 25;
                break;
            case R.id.rank36:
                type = 36;
                break;
            case R.id.rank49:
                type = 49;
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
        Button button = (Button) findViewById(v.getId());

        button.setBackgroundColor(Color.argb(255, 255, 234, 0));

        if (prevButtonId != v.getId()) {
            Button prevButton = (Button) findViewById(this.prevButtonId);
            prevButton.setBackgroundColor(Color.argb(255, 179, 136, 255));
        }
        this.prevButtonId = v.getId();
        Ranking ranking = getRaking(type);
        drawRanking(ranking);
    }

    private Ranking getRaking(int type) {

        Ranking ranking = new Ranking(this, String.valueOf(type));
        return ranking;
    }

    private void drawRanking(Ranking ranking) {
        LinearLayout r = (LinearLayout) findViewById(R.id.rankList);
        r.removeAllViews();
        if (ranking.ranking.size() == 0) {
            TextView textView = new TextView(this);
            textView.setText("Brak wyników!");
            textView.setTextSize(20);
            r.addView(textView);
        } else {
            Iterator<Score> rank = ranking.ranking.iterator();
            int i = 0;
            while (rank.hasNext()) {
                i++;
                Score score = rank.next();
                TextView textView = new TextView(this);
                textView.setText("" + i + ": " + score);// To string is override in Score
                textView.setTextSize(20);
                r.addView(textView);
            }
        }

    }


}
