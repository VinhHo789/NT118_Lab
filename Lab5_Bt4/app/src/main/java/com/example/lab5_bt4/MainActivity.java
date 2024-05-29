package com.example.lab5_bt4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import java.io.IOException;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private int playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.btnPlayPause);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    // If music is currently playing, pause it and save the playback position
                    mediaPlayer.pause();
                    playbackPosition = mediaPlayer.getCurrentPosition();
                    playButton.setText("Play");
                } else {
                    // If music is not playing or is paused, start or resume playback
                    String mp3Url = "https://jesusful.com/wp-content/uploads/music/2022/07/Alan_Walker_-_Faded_(Jesusful.com).mp3";
                    new PlayMusicTask().execute(mp3Url);
                    playButton.setText("Pause");
                }
                isPlaying = !isPlaying;
            }
        });
    }

    private class PlayMusicTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String mp3Url = params[0];
            try {
                if (!mediaPlayer.isPlaying()) {
                    // If mediaPlayer is not playing, set the data source and prepare
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(mp3Url);
                    mediaPlayer.prepare();
                }
                // If playbackPosition is not 0, set the playback position before starting
                if (playbackPosition > 0) {
                    mediaPlayer.seekTo(playbackPosition);
                }
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Update UI or do something after the music is played
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
