package com.example.micha.puzzle;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Michał on 2016-08-30.
 */
public class ChooseSource extends AppCompatActivity {

    Uri picUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public void choosePic(View v) {
        Intent i = new Intent(getApplicationContext(), ChooseImage.class);
        startActivity(i);
        finish();
    }

    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Puzzles/");

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void takePhoto(View v) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            File file = getOutputMediaFile(1);
            picUri = Uri.fromFile(file); // create
            i.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file

            startActivityForResult(i, 1);
        } else
            Toast.makeText(this, "Brak uprawnień do korzystania z kamery", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Intent i = new Intent(this, Game.class);
                    i.putExtra("path", picUri.getPath());
                    i.putExtra("drawable", false);
                    startActivity(i);
                    finish();
                    break;
            }
        }
    }


    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("picUri", picUri);
    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        picUri = savedInstanceState.getParcelable("picUri");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_source);
    }

}
