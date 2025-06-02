package com.example.lab6;

import android.content.Context;
import android.widget.Button;
import java.util.function.BiConsumer;

public class Cell {
    public final int row, col;
    public boolean isMine = false;
    public boolean isRevealed = false;
    public int neighborMines = 0;
    public Button button;

    public Cell(Context context, int row, int col, BiConsumer<Integer, Integer> onClick) {
        this.row = row;
        this.col = col;
        this.button = new Button(context);

        button.setOnClickListener(v -> onClick.accept(row, col));
        button.setPadding(0, 0, 0, 0);
        button.setTextSize(12);
    }

    public void reveal() {
        if (isRevealed) return;
        isRevealed = true;
        if (isMine) {
            button.setText("💣");
            button.setEnabled(false);
        } else {
            button.setText(neighborMines > 0 ? String.valueOf(neighborMines) : "");
            button.setEnabled(false);
        }
    }
}
