package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class eamonDev extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eamon_dev);

        videoView = findViewById(R.id.videoView1);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.eamon_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController  mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        //knows where it has to be poitioned on screen
        mediaController.setAnchorView(videoView);
    }
}
