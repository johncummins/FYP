package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fyp.ui.main.SectionsPagerAdapter_home;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {


    public void arCamera (View view){
        Log.i("Button pressed", "AR button has been pressed");
        Intent startARCamera = new Intent(this, DisplayARCameraActivity.class );
        startActivity(startARCamera);
    }

    public void showMaps (View view){
        Log.i("FloorPlanActivity button pressed", "FloorPlanActivity class is called");
        Intent startMaps = new Intent(this, FloorPlanActivity.class );
        startActivity(startMaps);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i("Here", "This is inside the home activity, onCreate method");
        SectionsPagerAdapter_home sectionsPagerAdapter_home = new SectionsPagerAdapter_home(this, getSupportFragmentManager());
        ViewPager viewPager_home = findViewById(R.id.view_pager_home);
        viewPager_home.setAdapter(sectionsPagerAdapter_home);
        TabLayout tabs = findViewById(R.id.tabs_home);
        tabs.setupWithViewPager(viewPager_home);
        findViewById(R.id.tabs_home).bringToFront();
    }

    public void showTrivia (View view){
        Log.i("TriviaActivity button pressed", "trivia class is called");
        Intent startQ = new Intent(this, StartQuizActivity.class );
        startActivity(startQ);
    }
}
