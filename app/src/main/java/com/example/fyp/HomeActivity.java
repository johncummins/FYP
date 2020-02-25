package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.fyp.ui.main.SectionsPagerAdapter;
import com.example.fyp.ui.main.SectionsPagerAdapter_home;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {


    public void arCamera (View view){
        Log.i("Button pressed", "AR button has been pressed");
        Intent startARCamera = new Intent(this, DisplayARCamera.class );
        startActivity(startARCamera);
    }

    public void showMaps (View view){
        Log.i("Maps button pressed", "Maps class is called");
        Intent startARCamera = new Intent(this, Maps.class );
        startActivity(startARCamera);
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
}
