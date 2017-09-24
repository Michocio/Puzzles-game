package com.example.micha.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;


public class Tile {


    private Coord newPosition;// Use in tiles swapping

    private int row, column;

    private DimensionsInt dimension;
    private Bitmap image;
    private ImageView view;

    private int id;


    private Board board;

    public void setNewPosition(Coord newPosition) {
        this.newPosition = newPosition;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Bitmap getImage() {
        return image;
    }

    public ImageView getView() {
        return view;
    }


    public Tile(Bitmap image, int id, Board board) {

        newPosition = new Coord();
        newPosition.setXY(0, 0);

        this.image = image; // Without border

        this.dimension = new DimensionsInt();
        this.dimension.width = image.getWidth();
        this.dimension.height = image.getHeight();

        this.id = id;
        this.board = board;
    }

    public void setView(ImageView v, final Context context) {
        this.view = v;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tile.this.board.TileCliked(Tile.this, true);
            }
        });
    }


    public void refresh(boolean changePos) {

        if (changePos) {
            this.view.setX(this.newPosition.getX());
            this.view.setY(this.newPosition.getY());
        }
        new CountDownTimer(500, 50) {
            int i = 60;

            public void onTick(long millisUntilFinished) {
                i -= 6;
                Tile.this.view.setColorFilter(Color.argb(i, 255, 255, 0), PorterDuff.Mode.SRC_ATOP);
            }

            public void onFinish() {
                Tile.this.view.setColorFilter(Color.argb(0, 255, 255, 0), PorterDuff.Mode.SRC_ATOP);
            }
        }.start();

        this.view.clearColorFilter();
    }


    public void highlight() {
        new CountDownTimer(Constant.HIGHLIGHT_TIME, Constant.COLOR_FILTER_TIME) {
            int i = 0;

            public void onTick(long millisUntilFinished) {
                i += 10;
                Tile.this.view.setColorFilter(Color.argb(i, 255, 255, 0), PorterDuff.Mode.SRC_ATOP);
            }

            public void onFinish() {
            }
        }.start();
    }

    public Coord getPositionWithoutBorders() {// Position transformed to original image
        Coord coord = new Coord();
        coord.setXY((int) this.row * this.image.getWidth(),
                (int) this.column * this.image.getHeight());
        return coord;
    }

}
