package com.example.lab3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {

    private static final String FILE_NAME = "results.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView tvStoredResults = findViewById(R.id.tvStoredResults);
        Button btnBack = findViewById(R.id.btnBack);

        StringBuilder data = new StringBuilder();
        try (FileInputStream fis = openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                data.append(line).append("\n");
            }

        } catch (Exception e) {
            data.append("Дані відсутні");
        }

        tvStoredResults.setText(data.toString());

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
