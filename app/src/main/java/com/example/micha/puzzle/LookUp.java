package com.example.micha.puzzle;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/*
    Activity simulates hint during game.
    Called after detecting swipe gesture.
 */

public class LookUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up);


        String path = this.getIntent().getStringExtra("path");
        int w = this.getIntent().getIntExtra("width", 0);
        int h = this.getIntent().getIntExtra("height", 0);

        DimensionsInt dim = new DimensionsInt();
        dim.width = w;
        dim.height = h;

        Bitmap bitmap = Picture.getPicture(path, dim, this);
        ImageView imageView = (ImageView) findViewById(R.id.lookup);
        imageView.setImageBitmap(bitmap);
    }

    public void back(View v) {
        finish();
    }
}
