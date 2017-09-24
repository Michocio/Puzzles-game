package com.example.micha.puzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    TextView text;
    private int numberOfTiles;


    static public int loadSettings(Activity activity) {
        SharedPreferences ranking = activity.getSharedPreferences(
                "settings", Context.MODE_PRIVATE);
        return ranking.getInt("numberOfTiles", 4);
    }

    public void saveSettings(View v) {
        SharedPreferences ranking = getApplicationContext().getSharedPreferences(
                "settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor write = ranking.edit();

        write.putInt("numberOfTiles", numberOfTiles);
        write.commit();

        Intent main = new Intent(this, Core.class);
        startActivity(main);
        finish();

    }

    private void seekBarController(SeekBar seekBar) {
        seekBar.setProgress((int) (Math.sqrt(numberOfTiles) - 2));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                text.setText(String.valueOf((int) Math.pow(i + 2, 2)));
                Settings.this.numberOfTiles = (int) Math.pow(i + 2, 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        int x = getIntent().getIntExtra("background", R.drawable.tiles_bg1);
        text = (TextView) findViewById(R.id.numberOfPuzzles);
        this.numberOfTiles = loadSettings(this);
        text.setText(String.valueOf(numberOfTiles));

        RelativeLayout background = (RelativeLayout) findViewById(R.id.background);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarController(seekBar);

        background.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), x));
    }
}
