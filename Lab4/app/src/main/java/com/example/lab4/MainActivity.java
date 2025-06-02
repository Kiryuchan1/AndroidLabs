package com.example.lab4;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.VideoView;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer audioPlayer;
    private VideoView videoView;

    private Spinner spinnerAudioFiles, spinnerVideoFiles;

    private Button btnAudioPlay, btnAudioPause, btnAudioStop;
    private Button btnVideoPlay, btnVideoPause, btnVideoStop;

    private String currentAudioFileName = null;
    private String currentVideoFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerAudioFiles = findViewById(R.id.spinnerAudioFiles);
        spinnerVideoFiles = findViewById(R.id.spinnerVideoFiles);

        btnAudioPlay = findViewById(R.id.btnAudioPlay);
        btnAudioPause = findViewById(R.id.btnAudioPause);
        btnAudioStop = findViewById(R.id.btnAudioStop);

        btnVideoPlay = findViewById(R.id.btnVideoPlay);
        btnVideoPause = findViewById(R.id.btnVideoPause);
        btnVideoStop = findViewById(R.id.btnVideoStop);

        videoView = findViewById(R.id.videoView);

        loadAudioFiles();
        loadVideoFiles();

        spinnerAudioFiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFile = (String) parent.getItemAtPosition(position);
                currentAudioFileName = selectedFile;
                initAudioPlayer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentAudioFileName = null;
                releaseAudioPlayer();
            }
        });

        spinnerVideoFiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedFile = (String) parent.getItemAtPosition(position);
                currentVideoFileName = selectedFile;
                initVideoPlayer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentVideoFileName = null;
                videoView.stopPlayback();
            }
        });

        btnAudioPlay.setOnClickListener(v -> {
            if (audioPlayer != null && !audioPlayer.isPlaying()) {
                audioPlayer.start();
            }
        });

        btnAudioPause.setOnClickListener(v -> {
            if (audioPlayer != null && audioPlayer.isPlaying()) {
                audioPlayer.pause();
            }
        });

        btnAudioStop.setOnClickListener(v -> {
            if (audioPlayer != null) {
                audioPlayer.stop();
                initAudioPlayer();
            }
        });

        btnVideoPlay.setOnClickListener(v -> {
            if (!videoView.isPlaying()) {
                videoView.start();
            }
        });

        btnVideoPause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        });

        btnVideoStop.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.stopPlayback();
                initVideoPlayer();
            }
        });
    }

    private void loadAudioFiles() {
        File dir = getFilesDir();
        File[] files = dir.listFiles();
        ArrayList<String> audioFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                    audioFiles.add(file.getName());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, audioFiles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioFiles.setAdapter(adapter);

        if (!audioFiles.isEmpty()) {
            currentAudioFileName = audioFiles.get(0);
            initAudioPlayer();
        }
    }

    private void loadVideoFiles() {
        File dir = getFilesDir();
        File[] files = dir.listFiles();
        ArrayList<String> videoFiles = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp4")) {
                    videoFiles.add(file.getName());
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, videoFiles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVideoFiles.setAdapter(adapter);

        if (!videoFiles.isEmpty()) {
            currentVideoFileName = videoFiles.get(0);
            initVideoPlayer();
        }
    }

    private void initAudioPlayer() {
        releaseAudioPlayer();

        if (currentAudioFileName == null) return;

        File audioFile = new File(getFilesDir(), currentAudioFileName);
        if (audioFile.exists()) {
            audioPlayer = MediaPlayer.create(this, Uri.fromFile(audioFile));
        }
    }

    private void releaseAudioPlayer() {
        if (audioPlayer != null) {
            if (audioPlayer.isPlaying()) {
                audioPlayer.stop();
            }
            audioPlayer.release();
            audioPlayer = null;
        }
    }

    private void initVideoPlayer() {
        if (currentVideoFileName == null) {
            videoView.stopPlayback();
            return;
        }

        File videoFile = new File(getFilesDir(), currentVideoFileName);
        if (videoFile.exists()) {
            videoView.setVideoURI(Uri.fromFile(videoFile));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseAudioPlayer();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}
