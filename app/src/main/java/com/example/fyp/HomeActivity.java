package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    public void arCamera (View view){

    ImageButton imageButton = findViewById(R.id.imageButton);
        Log.i("Info", " 1st image button has been pressed");

       // ImageButton imageButton5 = findViewById(R.id.imageButton5);
       // Log.i("Info", " 2nd image button has been pressed");

       // ImageButton imageButton6 = findViewById(R.id.imageButton6);
       // Log.i("Info", " 3rd image button has been pressed");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
