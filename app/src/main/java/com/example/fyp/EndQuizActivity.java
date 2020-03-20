package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndQuizActivity extends AppCompatActivity {

    Button restartButton;
    TextView yourScore, yourTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);
        restartButton = findViewById(R.id.Restart);
        yourTime = findViewById(R.id.yourTime);
        yourScore = findViewById(R.id.yourScore);
        String newNum = getIntent().getStringExtra("CorrectNum");
        Log.i("Here in end of quz", newNum);

        yourScore.setText("Your Score was: " + getIntent().getStringExtra("CorrectNum"));
        yourTime.setText("Your time was: " + getIntent().getStringExtra("Time"));
    }


    public void restartQuiz(View view){
        Log.i("Quiz button pressed", "quiz class is called, re start****");
        Intent startQ = new Intent(this, TriviaActivity.class );
        startActivity(startQ);

    }

}
