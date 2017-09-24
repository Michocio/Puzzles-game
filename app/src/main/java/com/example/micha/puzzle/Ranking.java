package com.example.micha.puzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Ranking {

    SharedPreferences.Editor write;
    List<Score> ranking;

    Ranking(Activity activity, String type) {
        ranking = new ArrayList<>();

        SharedPreferences ranking = activity.getSharedPreferences(
                "ranking" + type, Context.MODE_PRIVATE);

        write = ranking.edit();
        write.commit();

        if (ranking.contains("size") == false)// How many scores in table
            write.putInt("size", 0);
        write.commit();

        int size = ranking.getInt("size", 0);
        if (size > 0) {
            Map<String, ?> test = ranking.getAll();
            for (Map.Entry<String, ?> entry : test.entrySet()) {
                if (!entry.getKey().toString().equals("size")) {// Score

                    String parse[] = entry.getKey().split("_");

                    int times[] = parseFinishTime(entry.getValue().toString());
                    Score score = new Score(times[0], times[1], times[2], parse[0]);

                    this.ranking.add(score);
                }
            }

            Collections.sort(this.ranking);
            write.putInt("size", this.ranking.size());
            write.commit();
        }
    }


    public void addNewScore(String name, String time) {
        Iterator<Score> r = ranking.iterator();

        int times[] = parseFinishTime(time);
        Score score = new Score(times[0], times[1], times[2], name);

        Random rand = new Random();
        String hash = name + "_" + String.valueOf(rand.nextInt(1000));// Prevent overwriting the same nicknames

        if (ranking.size() > 0) {
            if (ranking.size() < Constant.NUMBER_OF_SCORES_RANKING ||
                    ranking.get(ranking.size() - 1).getVal() > score.getVal()) {
                ranking.add(score);
                write.putString(hash, time);
                Collections.sort(ranking);
            }
        } else {
            ranking.add(score);
            write.putString(hash, time);
        }

        write.putInt("size", ranking.size());
        write.commit();
    }

    private int[] parseFinishTime(String time) {
        // From string get minutes, seconds and miliseconds
        int[] times = new int[3];
        String[] parsed = time.split(",");
        for (int i = 0; i < parsed.length; i++)
            times[i] = Integer.parseInt(parsed[i]);
        return times;
    }

}
