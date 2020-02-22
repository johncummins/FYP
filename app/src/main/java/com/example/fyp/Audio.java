package com.example.fyp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Audio extends AppCompatActivity {


    public void playAudio (View view){
        Log.i("Here: play button pressed", "audio will play now");
        Intent startARCamera = new Intent(this, DisplayARCamera.class );
        startActivity(startARCamera);


        MediaPlayer mp = MediaPlayer.create(this, R.raw.proclamation_audio);
        mp.start();
    }
}

