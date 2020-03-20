package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartQuizActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        startButton = findViewById(R.id.startButton);

        //updates questions in trivia class
        //TriviaActivity triv = new TriviaActivity();
        //triv.updateQuestion();
    }

    public void enterQuiz(View view){
        Log.i("Quiz button pressed", "quiz class is called");
        Intent startQ = new Intent(this, TriviaActivity.class );
        startActivity(startQ);

    }
}
