package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {


    public void arCamera (View view){
        //MediaPlayer mediaPlayer;
        Log.i("Button pressed", "AR button has been pressed");
        Intent startARCamera = new Intent(this, DisplayARCamera.class );
        startActivity(startARCamera);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
