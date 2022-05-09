package com.example.asl_buddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class vocab extends AppCompatActivity {

    private CheckBox intro, ASL, English, intro_quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        // This callback will only be called when MyFragment is at least Started.


        intro = findViewById(R.id.checkBox);
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intro_intent = new Intent(getApplicationContext(), intro_video.class);
                startActivity(intro_intent);
            }
        });

        intro_quiz = findViewById(R.id.checkBox5);
        intro_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intro_quiz_intent= new Intent(getApplicationContext(), intro_quiz1.class);
               startActivity(intro_quiz_intent);
            }
        });


        ASL = findViewById(R.id.checkBox1);
        ASL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });


        English = findViewById(R.id.checkBox3);
        English.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}








