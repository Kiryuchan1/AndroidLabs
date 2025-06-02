package com.example.lab6;

import android.content.Context;
import java.util.Random;
import java.util.function.BiConsumer;

public class MineField {
    private final int rows, cols, mines;
    private final Cell[][] field;
    private final BiConsumer<Integer, Integer> onClick;
    private final Context context;

    public MineField(Context context, int rows, int cols, int mines, BiConsumer<Integer, Integer> onClick) {
        this.context = context;
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.onClick = onClick;
        this.field = new Cell[rows][cols];
        initField();
    }

    private void initField() {
        Random rand = new Random();
        int placed = 0;

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                field[r][c] = new Cell(context, r, c, onClick);

        while (placed < mines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!field[r][c].isMine) {
                field[r][c].isMine = true;
                placed++;
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                field[r][c].neighborMines = countNeighborMines(r, c);
            }
        }
    }

    private int countNeighborMines(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                int nr = row + dr;
                int nc = col + dc;
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols)
                    if (field[nr][nc].isMine) count++;
            }
        return count;
    }

    public void revealCell(int row, int col) {
        Cell cell = field[row][col];
        if (cell.isRevealed || cell.isMine) return;
        cell.reveal();
        if (cell.neighborMines == 0) {
            for (int dr = -1; dr <= 1; dr++)
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = row + dr;
                    int nc = col + dc;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols)
                        revealCell(nr, nc);
                }
        }
    }

    public Cell getCell(int row, int col) {
        return field[row][col];
    }
}
