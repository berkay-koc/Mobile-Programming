package com.example.myapplication;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    MediaPlayer music = new MediaPlayer();
    Uri trackUri;
    ImageButton playButton,pauseButton, prevButton, nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        String trackUriString = getIntent().getStringExtra("trackURI");
        trackUri = Uri.parse(trackUriString);
        prevButton = (ImageButton) findViewById(R.id.imageButton_prev);
        playButton = (ImageButton) findViewById(R.id.imageButton_play);
        pauseButton = (ImageButton) findViewById(R.id.imageButton_pause);
        nextButton = (ImageButton) findViewById(R.id.imageButton_next);
        music.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    musicPlay(trackUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicPause();
            }
        });
    }
    public void musicPlay(Uri uri) throws IOException {
        music.setDataSource(this, uri);
        music.prepare();
        music.start();
    }

    public void musicPause(){
        music.pause();
    }
}
