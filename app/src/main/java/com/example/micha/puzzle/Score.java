package com.example.micha.puzzle;

public class Score implements Comparable<Score> {
    private int val;
    private String name;
    private int min, sec, ms;


    public Score(int min, int sec, int ms, String name) {
        this.val = ms + sec * 10 + min * 600;
        this.name = name;
        this.min = min;
        this.sec = sec;
        this.ms = ms;
    }

    @Override
    public int compareTo(Score score) {
        if (score.val > val) return -1;
        if (score.val < val) return 1;
        return 0;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return this.name + ":  " + String.format("%02d", this.min) + ":" +
                String.format("%02d", this.sec) + ":" +
                String.format("%02d", this.ms);
    }
}