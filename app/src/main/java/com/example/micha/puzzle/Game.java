package com.example.micha.puzzle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


/*
    Activity handling pre game preparation and game loop
 */
public class Game extends AppCompatActivity {


    private void createFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void drawBoard(Board board) {
        RelativeLayout boardLayout = (RelativeLayout) findViewById(R.id.Board);
        board.setView(boardLayout);

        boardLayout.getLayoutParams().width = board.getDimensions().width;
        boardLayout.getLayoutParams().height = board.getDimensions().height;

        boardLayout.requestLayout();
    }


    private ImageView createImageViewFromTile(Tile tile, int posX, int posY) {
        ImageView nowy = new ImageView(this);
        nowy.setImageBitmap(Picture.getImageWithBorder(tile.getImage(), this));

        nowy.setX(posX);
        nowy.setY(posY);

        tile.setView(nowy, getApplicationContext());
        return nowy;
    }


    private void drawPuzzles(Board board, Bitmap image) {
        int id = 0;
        int posX = 0, posY = 0;
        for (int y = 0; y < board.getTilesInColumn(); y++) {
            posX = 0;
            for (int x = 0; x < board.getTilesInRow(); x++) {
                Coord pos = new Coord();
                pos.setXY(board.getTileDimensions().width * x, board.getTileDimensions().height * y);

                Bitmap bmp = Picture.cutOffImage(image, board.getTileDimensions(), pos);

                Coord boardPos = new Coord();
                boardPos.setXY(posX, posY);

                Tile tile = new Tile(bmp, id, board);
                tile.setRow(x);
                tile.setColumn(y);

                board.addTile(tile);
                board.getContener().addView(createImageViewFromTile(tile, posX, posY));

                id++;
                posX += Constant.TILE_BORDER_SIZE_DP * 2 + board.getTileDimensions().width;
            }
            posY += Constant.TILE_BORDER_SIZE_DP * 2 + board.getTileDimensions().height;
        }
    }


    void randomizePuzzlesAndStartGameLoop(final Board board) {
        // Use timer to get animation effect
        final Random rand = new Random();
        new CountDownTimer(Constant.RANDOMIZE_TIME, Constant.RANDOMIZE_TIME / Constant.NUMBER_OF_MIX) {
            public void onTick(long millisUntilFinished) {
                int a = rand.nextInt(board.tiles.size());
                int b = rand.nextInt(board.tiles.size());
                board.TileCliked(board.tiles.get(a), false);
                board.TileCliked(board.tiles.get(b), false);
            }

            public void onFinish() {
                gameLoop(board);
            }
        }.start();
    }

    private DimensionsInt adjustPictureDimensions(Board board) {
        int w = board.getDimensions().width - (board.getTilesInRow() * 2 * Constant.TILE_BORDER_SIZE_DP);
        int h = board.getDimensions().height - (board.getTilesInColumn() * 2 * Constant.TILE_BORDER_SIZE_DP);

        // Integer dimensions require integer result of dividing
        while (w % board.getTilesInRow() != 0)
            w--;

        while (h % board.getTilesInColumn() != 0)
            h--;
        return new DimensionsInt(w, h);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        createFullScreen();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_pref);


        final Board board = new Board(this);


        DimensionsInt dim = adjustPictureDimensions(board);

        String path = this.getIntent().getStringExtra("path");//Get chosen picture path


        Bitmap picture = Picture.getPicture(path, dim, this);
        RelativeLayout gameArena = (RelativeLayout) findViewById(R.id.gameArena);

        // Look for swipe action to display original picture
        SwipeListener swipeListener = new SwipeListener(gameArena, this, picture, path);

        board.setPicture(picture);
        board.countTileSize();

        drawBoard(board);

        drawPuzzles(board, picture);
        randomizePuzzlesAndStartGameLoop(board);
    }


    private void gameLoop(final Board board) {

        new CountDownTimer(40000000, 100) {
            // Simulates timer
            int x = 0;
            int m, s, ms;
            TextView min = (TextView) findViewById(R.id.min);
            TextView sec = (TextView) findViewById(R.id.sec);
            TextView milsec = (TextView) findViewById(R.id.milsec);

            public void onTick(long millisUntilFinished) {

                // Print clock
                ms++;
                milsec.setText(String.valueOf(ms % 10));
                s = ms / 10;
                sec.setText(String.valueOf(String.format("%02d", s % 60)) + ":");
                m = s / 60;
                min.setText(String.valueOf(String.format("%02d", m % 60)) + ":");

                if (m == 10)// Ten minutes limit
                {
                    this.cancel();
                    Intent main = new Intent(Game.this, Core.class);
                    startActivity(main);
                    finish();
                }
                boolean stop = Game.this.getIntent().getBooleanExtra("stop", false);
                if (stop) // Picture arranged in proper way, game is finished
                {
                    int score = ms + s * 10 + m * 600;

                    Intent i = new Intent(Game.this, GameFinished.class);
                    // Pass informations
                    i.putExtra("score", m % 60 + "," + s % 60 + "," + ms % 10);
                    i.putExtra("size", board.getTilesInColumn() * board.getTilesInRow());
                    startActivity(i);


                    this.cancel();
                }
            }

            public void onFinish() {

            }
        }.start();
    }
}