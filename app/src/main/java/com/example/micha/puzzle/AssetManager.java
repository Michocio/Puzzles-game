package com.example.micha.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Controles "communication" with external storage.
 */
public class AssetManager {
    static public Bitmap picture;


    public static boolean isExternalStorageWritable() {
        // Check external storage status
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    static boolean createFolder() {
        if (isExternalStorageWritable() == true) {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constant.FOLDER_NAME);
            if (!file.mkdirs())
                return false;
            return true;
        }
        return false;
    }

    /*
        List all pictures available to be used in game.
        Puzzles directory is placed in public pictures
        and can be seen in android gallery. To add
        picture to game, ona can choose option take photo during game,
        or move some images to folder puzzles.
     */
    static Pair<Vector<Bitmap>, Vector<String>> getAllPictures() {

        Vector<Bitmap> bitmaps = new Vector<>();
        Vector<String> paths = new Vector<>(); // Paths to files with images

        String path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString() + "/puzzles";
        File f = new File(path);
        File file[] = f.listFiles();

        for (File it : file) {
            String filenameArray[] = it.getName().split("\\.");
            String extension = filenameArray[filenameArray.length - 1];

            if (extension.equals("png") || extension.equals("jpg") ||
                    extension.equals("bmp") || extension.equals("gif") || extension.equals("jpeg")) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = null;
                try {
                    bitmap = Picture.decodeSampledBitmapFromFile(it, Constant.GRID_THUMB_DIM,
                            Constant.GRID_THUMB_DIM);//Dimensions for grid thumbs
                    bitmaps.add(bitmap);
                    paths.add(path + "/" + it.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
        return new Pair<>(bitmaps, paths);
    }

}
