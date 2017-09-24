package com.example.micha.puzzle;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

/*
    Main screen, visible after launching the game.
 */

public class Core extends AppCompatActivity {

    final int MY_PERMISSIONS_REQUEST_WRITE = 77;
    final int MY_PERMISSIONS_REQUEST_CAMERA = 99;

    /* Methods triggered after clicking button with particular name*/
    public void ranking(View v) {
        Intent i = new Intent(getApplicationContext(), RankingTable.class);
        startActivity(i);
    }

    public void exit(View v) {
        finish();
    }

    public void newGame(View v) {
        Intent i = new Intent(Core.this, ChooseSource.class);
        startActivity(i);
    }

    public void settings(View v) {
        Intent settingsActivity = new Intent(Core.this, Settings.class);
        settingsActivity.putExtra("type", Settings.loadSettings(this));
        startActivity(settingsActivity);
        settingsActivity.putExtra("background", R.drawable.tiles_bg3);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Constant.PACKAGE_NAME = getApplicationContext().getPackageName();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permission.requestWriting(this);
        Permission.requestCamera(this);
        AssetManager.createFolder();// Check if exists, and if not, create folder for pictures
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this,
                            "Brak uprawnień może skutkować ograniczeniem funkcjonalność programu!",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this,
                            "Brak uprawnień może skutkować ograniczeniem funkcjonalność programu!",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}