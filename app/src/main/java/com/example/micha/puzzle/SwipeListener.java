package com.example.micha.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class SwipeListener {


    int howManyTimes = Constant.HOW_MANY_TIMES_CAN_LOOK;

    public SwipeListener(View view, final Activity activity, final Bitmap bitmap, final String path) {

        view.setOnTouchListener(new View.OnTouchListener() {
            float startX = 0, startY = 0;
            boolean check = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = MotionEventCompat.getActionMasked(motionEvent);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        this.startX = motionEvent.getX();
                        this.startY = motionEvent.getY();
                        if (this.startX < 200) check = true;
                        else check = false;
                        return true;
                    case (MotionEvent.ACTION_UP):
                        if (check) {
                            // Detect HORIZONTAL swipe from left to right
                            if (motionEvent.getX() > this.startX &&
                                    Math.abs(this.startY - motionEvent.getY()) < 600) {
                                if (howManyTimes > 0) {
                                    Intent i = new Intent(activity, LookUp.class);
                                    i.putExtra("path", path);
                                    i.putExtra("width", bitmap.getWidth());
                                    i.putExtra("height", bitmap.getHeight());
                                    activity.startActivity(i);
                                    howManyTimes--;
                                } else
                                    Toast.makeText(activity.getApplicationContext(),
                                            "Nie możesz więcej razy podejrzeć obrazka", Toast.LENGTH_SHORT).show();

                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


}
