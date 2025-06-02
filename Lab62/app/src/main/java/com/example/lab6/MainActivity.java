package com.example.lab6;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int ROWS = 8;
    private static final int COLS = 10;
    private static final int MINES = 20;

    private MineField mineField;
    private GridLayout grid;
    private FrameLayout rootLayout;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);

        grid = new GridLayout(this);
        grid.setRowCount(ROWS);
        grid.setColumnCount(COLS);
        grid.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        rootLayout.addView(grid);

        restartButton = new Button(this);
        restartButton.setText("Грати ще");
        restartButton.setVisibility(View.GONE);

        FrameLayout.LayoutParams btnParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        btnParams.gravity = Gravity.CENTER;
        restartButton.setLayoutParams(btnParams);

        rootLayout.addView(restartButton);

        restartButton.setOnClickListener(v -> restartGame());

        startNewGame();
    }

    private void startNewGame() {
        mineField = new MineField(this, ROWS, COLS, MINES, this::onCellClicked);
        drawField();
        restartButton.setVisibility(View.GONE);
    }

    private void drawField() {
        grid.removeAllViews();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                grid.addView(mineField.getCell(row, col).button);
            }
        }
    }

    private void onCellClicked(int row, int col) {
        Cell cell = mineField.getCell(row, col);
        if (cell.isRevealed) return;

        if (cell.isMine) {
            cell.reveal();
            Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
            revealAll();
            restartButton.setVisibility(View.VISIBLE);
        } else {
            mineField.revealCell(row, col);
        }
    }

    private void revealAll() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                mineField.getCell(r, c).reveal();
            }
        }
    }

    private void restartGame() {
        startNewGame();
    }
}
