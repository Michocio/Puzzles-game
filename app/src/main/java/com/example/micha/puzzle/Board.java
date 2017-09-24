package com.example.micha.puzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.RelativeLayout;

import java.util.Vector;

/**
 * Class controls all interacts and process connected with
 * game's board.
 */
public class Board {

    private DimensionsInt tileDimensions;
    private DimensionsInt dimensions;// Width and height of screen

    private RelativeLayout view;

    private int tilesInRow = 2, tilesInColumn = 2; // Default values
    Vector<Tile> tiles = new Vector<>();

    // Used in tiles swapping
    Tile firstlyClicked = null;
    Tile secondlyClicked = null;

    Activity activity;

    Bitmap picture;// Starting picture == arranged puzzles


    /*
        Constructor, basing on screen dimensions
         determines dimensions of board.
     */
    Board(Activity activity) {
        DimensionsInt screenDimensions = Screen.getScreenDimensions(activity);

        dimensions = new DimensionsInt();
        this.dimensions.width = screenDimensions.width -
                Screen.dpToPx(Constant.BOARD_MARGIN_HORIZONTAL_DP, activity);

        activity.findViewById(R.id.timer).measure(0, 0);// Right below timer
        this.dimensions.height = screenDimensions.height -
                activity.findViewById(R.id.timer).getMeasuredHeight() -
                Constant.BOARD_MARGIN_VERTICAL_DP;

        this.activity = activity;
        loadSettings();// How many tiles in row and column
    }


    public void countTileSize() {
        this.tileDimensions = new DimensionsInt(
                (int) (picture.getWidth()) / (tilesInRow),
                (int) picture.getHeight() / (tilesInColumn));
    }


    public void TileCliked(Tile tile, boolean check) {//Handles event of clicking the tile.
        if (this.firstlyClicked == null) {
            this.firstlyClicked = tile;
            tile.highlight();
        } else {
            if (this.firstlyClicked != tile) {// Two different tiles clicked, swap puzzles
                this.secondlyClicked = tile;
                swapTiles();
                this.firstlyClicked = null;
                this.secondlyClicked = null;
            } else {
                this.firstlyClicked.refresh(false);
                this.firstlyClicked = null;
            }

        }
        if (check)// If puzzles are arranged in starting picture
            checkPuzzles();
    }

    public Bitmap checkPuzzles() {/* Checks if puzzles are arranged in proper way.
        Comparing whole pictures, instead of checking only ordering of titles,
        is necessary, because of possibility that many tiles can be the same.
        Thanks to that we are able to switch the same tiles in random way,
        and if starting picture is the same as current picture, game will
        be finished.
    */
        Bitmap bitmap = Bitmap.createBitmap(this.picture.getWidth(),
                this.picture.getHeight(), this.picture.getConfig());

        Canvas canvas = new Canvas(bitmap);

        for (int i = 0; i < this.tiles.size(); i++)// Fold parts of image from tiles into one picture
            canvas.drawBitmap(this.tiles.get(i).getImage(), this.tiles.get(i).getPositionWithoutBorders().getX(),
                    this.tiles.get(i).getPositionWithoutBorders().getY(), null);


        if (bitmap.sameAs(this.picture) == true)
            activity.getIntent().putExtra("stop", true);// Give a sign to parent activity to stop game
        else
            activity.getIntent().putExtra("stop", false);
        return bitmap;
    }

    private void swapTiles() {
        /*
            Simulates two tiles swapping. Nothing interesting below,
            only many lines of assigning values.
         */
        Coord first = new Coord();
        first.setXY((int) firstlyClicked.getView().getX(), (int) firstlyClicked.getView().getY());

        Coord second = new Coord();
        second.setXY((int) secondlyClicked.getView().getX(), (int) secondlyClicked.getView().getY());

        this.firstlyClicked.setNewPosition(second);
        this.secondlyClicked.setNewPosition(first);
        int tmpX, tmpY;
        tmpX = firstlyClicked.getRow();
        tmpY = firstlyClicked.getColumn();

        this.firstlyClicked.setRow(this.secondlyClicked.getRow());
        this.firstlyClicked.setColumn(this.secondlyClicked.getColumn());

        this.secondlyClicked.setRow(tmpX);
        this.secondlyClicked.setColumn(tmpY);

        firstlyClicked.refresh(true);
        secondlyClicked.refresh(true);
    }


    private void loadSettings() {
        SharedPreferences ranking = activity.getSharedPreferences(
                "settings", Context.MODE_PRIVATE);
        this.tilesInRow = (int) Math.sqrt(ranking.getInt("numberOfTiles", 4));
        this.tilesInColumn = this.tilesInRow;
    }


    /*
        Getters and setters section below.
     */
    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public int getTilesInColumn() {
        return tilesInColumn;
    }

    public int getTilesInRow() {
        return tilesInRow;
    }

    public DimensionsInt getDimensions() {
        return dimensions;
    }

    public DimensionsInt getTileDimensions() {
        return tileDimensions;
    }

    public void setView(RelativeLayout view) {
        this.view = view;
    }

    public RelativeLayout getContener() {
        return view;
    }

    public void addTile(Tile tile) {
        this.tiles.addElement(tile);
    }
}
