package com.example.micha.puzzle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.Vector;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    Bitmap image;
    ImageView view;
    Activity activity;

    // references to our images
    private Vector<Bitmap> thumbs;
    private Vector<String> paths;

    public Bitmap getImage(int position) {
        return thumbs.get(position);
    }

    public String getPath(int position) {
        return paths.get(position);
    }

    private Vector<Bitmap> getAllPicsFromRes() {
        Vector<Bitmap> pics = new Vector<>();
        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {// Lookup on all drawables
            try {
                if (f.getName().contains("tiles_")) {// Check i drawable should be considered as picture for game
                    pics.add(Picture.decodeSampledBitmapFromResource(mContext.getResources(),
                            f.getInt(null), Constant.GRID_THUMB_DIM,
                            Constant.GRID_THUMB_DIM));
                    paths.add("drawable/" + f.getInt(null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pics;
    }

    private Pair<Vector<Bitmap>, Vector<String>> getAllPicsFromGallery() {
        if (AssetManager.isExternalStorageWritable())
            return AssetManager.getAllPictures();
        else
            return null;
    }


    public ImageAdapter(Context c, Activity activity) {
        // List all pictures that could be used in game
        thumbs = new Vector<>();
        paths = new Vector<>();
        mContext = c;
        this.activity = activity;

        /* Two sources:
            -> Drawables
            -> Folder from gallery
         */
        thumbs.addAll(getAllPicsFromRes());
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            thumbs.addAll(getAllPicsFromGallery().first);
            paths.addAll(getAllPicsFromGallery().second);
        }
    }


    public int getCount() {
        return thumbs.size();
    }

    public Bitmap getItem(int position) {
        return this.image;
    }

    public long getItemId(int position) {
        return 0;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(Screen.dpToPx(100, activity),
                    Screen.dpToPx(100, activity)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Bitmap b = thumbs.get(position);

        imageView.setImageBitmap(b);
        this.view = imageView;
        this.image = b;
        return imageView;
    }

}