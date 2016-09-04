package com.example.android.ukonnect;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class awnmkjteonawjketawbnejuilthbuawjiletgbuilawegutilagweu extends AppCompatActivity {
    int[][] board;
    Button topleftb, topmidb, toprightb, midleftb, midmidb, midrightb, botleftb, botmidb, botrightb;
    Boolean turn;
    static int red, blue;
    int count;
    LinearLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awnmkjteonawjketawbnejuilthbuawjiletgbuilawegutilagweu);
        setTitle("╭∩╮(︶︿︶)╭∩╮  ╭∩╮(︶︿︶)╭∩╮ ╭∩╮(︶︿︶)╭∩╮ ╭∩╮(︶︿︶)╭∩╮ ╭∩╮(︶︿︶)╭∩╮ ╭∩╮(︶︿︶)╭∩╮ ╭∩╮(︶︿︶)╭∩╮ ");
        start();
        grid = (LinearLayout) findViewById(R.id.grid);
        red = -65536;
        blue = -16776961;
        count = 0;
        board = new int[3][3];
        turn = true;

        grid.setBackgroundColor(red);

        topleftb = (Button) findViewById(R.id.t0x0);
        topmidb = (Button) findViewById(R.id.t1x0);
        toprightb = (Button) findViewById(R.id.t2x0);

        midleftb = (Button) findViewById(R.id.m0x1);
        midmidb = (Button) findViewById(R.id.m1x1);
        midrightb = (Button) findViewById(R.id.m2x1);

        botleftb = (Button) findViewById(R.id.b0x2);
        botmidb = (Button) findViewById(R.id.b1x2);
        botrightb = (Button) findViewById(R.id.b2x2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void topleft(View view) {
        if (turn) {
            topleftb.setText("X");
            topleftb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            topleftb.setTextColor(red);
            board[0][2] = red;

            turn = false;
            checkWinner(0, 2);
        } else {
            topleftb.setText("O");
            topleftb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            topleftb.setTextColor(blue);
            board[0][2] = blue;

            turn = true;
            checkWinner(0, 2);
        }
        topleftb.setEnabled(false);
    }

    public void topmid(View view) {
        if (turn) {
            topmidb.setText("X");
            topmidb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            topmidb.setTextColor(red);
            board[1][2] = red;
            turn = false;
            checkWinner(1, 2);
        } else {
            topmidb.setText("O");
            topmidb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            topmidb.setTextColor(blue);
            board[1][2] = blue;
            turn = true;
            checkWinner(1, 2);
        }
        topmidb.setEnabled(false);
    }

    public void topright(View view) {
        if (turn) {
            toprightb.setText("X");
            toprightb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            toprightb.setTextColor(red);
            board[2][2] = red;
            turn = false;
            checkWinner(2, 2);
        } else {
            toprightb.setText("O");
            toprightb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            toprightb.setTextColor(blue);
            board[2][2] = blue;
            turn = true;
            checkWinner(2, 2);
        }
        toprightb.setEnabled(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void midleft(View view) {
        if (turn) {
            midleftb.setText("X");
            midleftb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            midleftb.setTextColor(red);
            board[0][1] = red;
            turn = false;
            checkWinner(0, 1);
        } else {
            midleftb.setText("O");
            midleftb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            midleftb.setTextColor(blue);
            board[0][1] = blue;
            turn = true;
            checkWinner(0, 1);
        }
        midleftb.setEnabled(false);
    }

    public void midmid(View view) {
        if (turn) {
            midmidb.setText("X");
            midmidb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            midmidb.setTextColor(red);
            board[1][1] = red;
            turn = false;
            checkWinner(1, 1);
        } else {
            midmidb.setText("O");
            midmidb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            midmidb.setTextColor(blue);
            board[1][1] = blue;
            turn = true;
            checkWinner(1, 1);
        }
        midmidb.setEnabled(false);
    }

    public void midright(View view) {
        if (turn) {
            midrightb.setText("X");
            midrightb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            midrightb.setTextColor(red);
            board[2][1] = red;
            turn = false;
            checkWinner(2, 1);
        } else {
            midrightb.setText("O");
            midrightb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            midrightb.setTextColor(blue);
            board[2][1] = blue;
            turn = true;
            checkWinner(2, 1);
        }
        midrightb.setEnabled(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void botleft(View view) {
        if (turn) {
            botleftb.setText("X");
            botleftb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            botleftb.setTextColor(red);
            board[0][0] = red;
            turn = false;
            checkWinner(0, 0);
        } else {
            botleftb.setText("O");
            botleftb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            botleftb.setTextColor(blue);
            board[0][0] = blue;
            turn = true;
            checkWinner(0, 0);
        }
        botleftb.setEnabled(false);
    }

    public void botmid(View view) {
        if (turn) {
            botmidb.setText("X");
            botmidb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            botmidb.setTextColor(red);
            board[1][0] = red;
            turn = false;
            checkWinner(1, 0);
        } else {
            botmidb.setText("O");
            botmidb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            botmidb.setTextColor(blue);
            board[1][0] = blue;
            turn = true;
            checkWinner(1, 0);
        }
        botmidb.setEnabled(false);
    }

    public void botright(View view) {
        if (turn) {
            botrightb.setText("X");
            botrightb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            botrightb.setTextColor(red);
            board[2][0] = red;
            turn = false;
            checkWinner(2, 0);
        } else {
            botrightb.setText("O");
            botrightb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);
            botrightb.setTextColor(blue);
            board[2][0] = blue;
            turn = true;
            checkWinner(2, 0);
        }
        botrightb.setEnabled(false);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    public void checkWinner(int row, int col) {
        count++;
        boolean winner = checkWinnerHelper(row, col);

        if (turn) {
            grid.setBackgroundColor(red);
        } else {
            grid.setBackgroundColor(blue);
        }

        if (winner) {
            grid.setBackgroundColor(Color.TRANSPARENT);
            if (board[row][col] == red) {
                redWins();
                grid.removeAllViews();
                TextView temp = new TextView(this);
                temp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
                temp.setTextColor(red);
                temp.setText("RED WINS!!!");
                temp.setGravity(Gravity.CENTER);
                grid.addView(temp);
            } else {
                blueWins();
                grid.removeAllViews();
                TextView temp = new TextView(this);
                temp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
                temp.setTextColor(blue);
                temp.setText("BLUE WINS!!!");
                temp.setGravity(Gravity.CENTER);
                grid.addView(temp);
            }

        } else if (count == 9) {
            grid.setBackgroundColor(Color.TRANSPARENT);
            draw();
            grid.removeAllViews();
            TextView temp = new TextView(this);
            temp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
            temp.setTextColor(-65281);
            temp.setText("DRAW!!!");
            temp.setGravity(Gravity.CENTER);
            grid.addView(temp);

        }

    }

    public boolean checkWinnerHelper(int row, int col) {
        String Player = String.valueOf(board[row][col]);
        String boardchar[][] = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardchar[i][j] = String.valueOf(board[i][j]);
            }
        }

        int r = row;
        int c = col;

        boolean onDiagonal = (row == col) || (col == -1 * row + (board.length - 1));
        boolean HorizontalWin = true, VerticalWin = true;
        boolean DiagonalWinOne = true, DiagonalWinTwo = true;

        // Check the rows and columns
        for (int n = 0; n < board.length; n++) {
            if (!boardchar[r][n].equals(Player))
                HorizontalWin = false;
            if (!boardchar[n][c].equals(Player))
                VerticalWin = false;
        }

        // Only check diagonals if the move is on a diagonal
        if (onDiagonal) {
            // Check the diagonals
            for (int n = 0; n < board.length; n++) {
                if (!boardchar[n][n].equals(Player))
                    DiagonalWinOne = false;
                if (!boardchar[n][-1 * n + (board.length - 1)].equals(Player))
                    DiagonalWinTwo = false;
            }
        } else {
            DiagonalWinOne = false;
            DiagonalWinTwo = false;
        }

        boolean hasWon = (HorizontalWin || VerticalWin || DiagonalWinOne || DiagonalWinTwo);

        return hasWon;
    }

    ////////////////////////////////////////////////////////////////////////SOUNDS//////////////////////////////////////////////////
    public void start() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.start);
        mp.start();
    }

    public void redWins() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.redwins);
        mp.start();
    }

    public void blueWins() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.bluewins);
        mp.start();
    }

    public void draw() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.draw);
        mp.start();
    }
}
