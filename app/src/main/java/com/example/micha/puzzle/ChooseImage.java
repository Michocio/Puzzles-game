package com.example.micha.puzzle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/*
    Activity allows user choose image to game with.
 */
public class ChooseImage extends AppCompatActivity {

    String path;
    ImageView lastClicked = null;
    Bitmap image;
    int id = 0;
    ImageAdapter adapter;
    Boolean drawable = false;

    public void newGameStart(View v) {
        if (lastClicked != null) {// Pass informations about chosen picture for game activity.
            Intent i = new Intent(this, Game.class);
            i.putExtra("path", path);
            i.putExtra("drawable", drawable);
            startActivity(i);
            finish();
        } else
            Toast.makeText(this, "Wybierz obrazek!", Toast.LENGTH_SHORT).show();
    }

    private void handleImageClick(final GridView gridview) {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (lastClicked != null)
                    lastClicked.clearColorFilter();

                // After scrolling getChildAt() needs adjusted position:
                int firstPosition = gridview.getFirstVisiblePosition();
                int childPosition = position - firstPosition;

                ImageView imageView = (ImageView) gridview.getChildAt(childPosition);
                lastClicked = imageView;
                image = adapter.getImage(position);// Position in vector is constant even after scrolling
                path = adapter.getPath(position);

                /* If path contains drawable/,
                     than that is sign that picture should be taken from drawables.
                */
                if (adapter.getPath(position).contains("drawable/"))
                    drawable = true;

                AssetManager.picture = image;
                ChooseImage.this.id = (int) id;
                imageView.setColorFilter(Color.argb(80, 255, 255, 0));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);

        final GridView gridview = (GridView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this, this);
        gridview.setAdapter(adapter);
        handleImageClick(gridview);
    }
}