package com.example.asl_buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class question2 extends AppCompatActivity implements View.OnClickListener{

    private Button correct1;
    private Button wrong2;
    private Button wrong1;
    private Button wrong3;
    private MediaPlayer bonky;
    private MediaPlayer correct;
    private MediaPlayer passed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        correct1=findViewById(R.id.button3);
        wrong1=findViewById(R.id.button7);
        wrong2=findViewById(R.id.button8);
        wrong3=findViewById(R.id.button9);

        correct1.setOnClickListener(this);
        wrong1.setOnClickListener(this);
        wrong2.setOnClickListener(this);
        wrong3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        bonky= MediaPlayer.create(this,R.raw.bonk);
        correct=MediaPlayer.create(this,R.raw.correct);
        passed=MediaPlayer.create(this,R.raw.pass);
        switch (v.getId())
        {

            case R.id.button3:
                passed.start();
                Toast.makeText(question2.this,"Correct answer! Quiz passed!",Toast.LENGTH_LONG).show();
                finish();
                break;

            case R.id.button8:
                bonky.start();
                Toast.makeText(question2.this,"Wrong answer!",Toast.LENGTH_LONG).show();
                break;

            case R.id.button7:
                bonky.start();
                Toast.makeText(question2.this,"Wrong answer!",Toast.LENGTH_LONG).show();
                break;

            case R.id.button9:
                bonky.start();
                Toast.makeText(question2.this,"Wrong answer!",Toast.LENGTH_LONG).show();
                break;
            //add counter

        }

    }


}