package com.example.micha.puzzle;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Everything what is connected with pictures tooling.
 */
public class Picture {

    public static Bitmap getPicture(Resources resources, int resId, DimensionsInt dimensions) {
        return Bitmap.createBitmap(decodeSampledBitmapFromResource(resources,
                resId, dimensions.width, dimensions.height));
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, resId, options),
                reqWidth, reqHeight, false);
    }

    public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth, int reqHeight) throws FileNotFoundException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new FileInputStream(file), null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return Bitmap.createScaledBitmap(BitmapFactory.decodeStream(new FileInputStream(file), null, options),
                reqWidth, reqHeight, false);
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) throws FileNotFoundException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(path),
                reqWidth, reqHeight, false);
    }

    public static Bitmap getPicture(String path, DimensionsInt dim, Activity activity) {

        if (path.contains("drawable/")) {
            String array[] = path.split("/");
            int resId = new Integer(array[1]);
            return Picture.getPicture(activity.getResources(), resId, dim);
        } else {
            try {
                return Picture.decodeSampledBitmapFromPath(path, dim.width, dim.height);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap cutOffImage(Bitmap image, DimensionsInt dimensions, Coord pos) {
        return Bitmap.createBitmap(image, pos.getX(), pos.getY(), dimensions.width, dimensions.height);
    }

    public static Bitmap getImageWithBorder(Bitmap image, Activity activity) {

        Bitmap window = Bitmap.createBitmap(image.getWidth() + 2 * Constant.TILE_BORDER_SIZE_DP,
                image.getHeight() + 2 * Constant.TILE_BORDER_SIZE_DP,
                image.getConfig());

        Canvas canvas = new Canvas(window);
        canvas.drawColor(Color.BLACK);//Border
        canvas.drawBitmap(image, Constant.TILE_BORDER_SIZE_DP, Constant.TILE_BORDER_SIZE_DP, null);

        return window;
    }


}
