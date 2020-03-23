package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DecimalFormat;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class TriviaActivity extends AppCompatActivity {

    private TextView questionBox, questionNum, score;
    private Button bt1, bt2, bt3, bt4;
    private int correct;
    private int qNum = 0;
    private String answer;
    private RadioGroup btGroup;
    DatabaseReference rootRef, childRef, newRef;
    public Chronometer chronometer;
    private boolean chronRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        chronometer = findViewById(R.id.chron);
        questionBox = findViewById(R.id.questionBox);
        questionNum = findViewById(R.id.questionNumber);
        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        bt4 = findViewById(R.id.button4);
        btGroup = findViewById(R.id.radio_group);
        score = findViewById(R.id.score);
        Toolbar myToolbar = findViewById(R.id.toolbarTrivia);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Easter Rising Quiz");

        //initial call on the update question function, initialises the the questions and answers
        updateQuestion();
        //calls the timer function
        startChron();

        //button 1
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(bt1.getText().equals(answer)){
                    bt1.setBackgroundColor(GREEN);
                    Toast.makeText(TriviaActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    correct++;
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();
                }
                else{
                    bt1.setBackgroundColor(RED);
                    Toast.makeText(TriviaActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();

                }
            }
        });

        //button 2
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(bt2.getText().equals(answer)){
                    bt2.setBackgroundColor(GREEN);
                    Toast.makeText(TriviaActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    correct++;
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();
                }
                else{
                    bt2.setBackgroundColor(RED);
                    Toast.makeText(TriviaActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();

                }
            }
        });

        //button 3
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(bt3.getText().equals(answer)){
                    bt3.setBackgroundColor(GREEN);
                    Toast.makeText(TriviaActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    correct++;
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();

                }
                else{
                    bt3.setBackgroundColor(RED);
                    Toast.makeText(TriviaActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();

                }
            }
        });

        //button 4
        bt4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(bt4.getText().equals(answer)){
                    bt4.setBackgroundColor(GREEN);
                    Toast.makeText(TriviaActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    correct++;
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();
                }
                else{
                    bt4.setBackgroundColor(RED);
                    Toast.makeText(TriviaActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                    updateQuestionNum();
                    updateScore();


                }
            }
        });

    }

    //function to start the timer
    public void startChron(){
        Log.i("Here", "This is chron starting");
        if(!chronRunning){
            chronometer.start();
            chronRunning = true;
        }
    }



    public void updateQuestionNum(){
        questionNum.setText("Question "+ qNum);

    }

    public void updateScore(){
        int newNum = qNum-1;
        score.setText("Score: " +correct + "/" +newNum);
    }

    public void updateQuestion() {
        qNum++;
        Log.i("this is inside update question", "qNum is: " + qNum);

        if (qNum > 10) {
            chronometer.stop();
            chronRunning = false;

            //setting question box to invisible
            questionNum.setVisibility(View.INVISIBLE);

            double elapsed = (int)(SystemClock.elapsedRealtime()-chronometer.getBase());

            for(int i=1;i<=3;i++)
            {
                elapsed = elapsed*.1;
            }

            DecimalFormat df = new DecimalFormat("#.##");
            String elapsedTime = df.format(elapsed);
            String yourCorrectScore = Integer.toString(correct);

            //calls end of quiz screen, shows score and can restart
            Intent endQ = new Intent(this, EndQuizActivity.class );

            //passing score and time of user to the new intent, which is in end of quiz activity
            endQ.putExtra("CorrectNum", yourCorrectScore);
            endQ.putExtra("Time", elapsedTime);
            startActivity(endQ);

        } else {

            //database reference pointing to root of database
            rootRef = FirebaseDatabase.getInstance().getReference();

            //database reference pointing to child node questions
            childRef = rootRef.child("Questions");

                    //question
                    newRef = childRef.child(Integer.toString(qNum));
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String qName = dataSnapshot.child("question").getValue().toString();
                            questionBox.setText(qName);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //choice 1
                    newRef = childRef.child(Integer.toString(qNum));
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String choice1 = dataSnapshot.child("option1").getValue().toString();
                            bt1.setText(choice1);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //choice 2
                    newRef = childRef.child(Integer.toString(qNum));
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String choice2 = dataSnapshot.child("option2").getValue().toString();
                            bt2.setText(choice2);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //choice 3
                    newRef = childRef.child(Integer.toString(qNum));
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String choice3 = dataSnapshot.child("option3").getValue().toString();
                            bt3.setText(choice3);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //choice 4
                    newRef = childRef.child(Integer.toString(qNum));
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String choice3 = dataSnapshot.child("option4").getValue().toString();
                            bt4.setText(choice3);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //answer 4
                    newRef = childRef.child(Integer.toString(qNum));
                    newRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           answer = dataSnapshot.child("answer").getValue().toString();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
        updateQuestionNum();
    }
}