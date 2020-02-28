package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Trivia extends AppCompatActivity {


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);


        Toolbar myToolbar = findViewById(R.id.toolbarTrivia);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Test your Knowledge");



        db = FirebaseFirestore.getInstance();

        //add new contact to address book
        Map<String,Object> newContact = new HashMap<>();
        newContact.put("Name", "Cummins");
        newContact.put("Email", "johncummins1997@gmail.com");
        newContact.put("Number", "0879801676");

        db.collection("AddressBook").document("1")
                .set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Here db", "Contact has been added");
                        Toast.makeText(Trivia.this, "Contact has been added, Toast", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Error", e.getMessage());

                    }
                });
    }
}
