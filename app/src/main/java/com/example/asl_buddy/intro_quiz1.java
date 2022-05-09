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



public class intro_quiz1 extends AppCompatActivity implements View.OnClickListener{
    private ImageButton hellobut;
    private ImageView wrong1;
    private ImageView wrong2;
    private ImageView wrong3;
    private MediaPlayer bonky;
    private MediaPlayer correct;
    private MediaPlayer passed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_quiz1);

        hellobut=findViewById(R.id.imageButton3);
        wrong1=findViewById(R.id.imageView3);
        wrong2=findViewById(R.id.imageView4);
        wrong3=findViewById(R.id.imageView7);

        hellobut.setOnClickListener(this);
        wrong1.setOnClickListener(this);
        wrong2.setOnClickListener(this);
        wrong3.setOnClickListener(this);


    }


    @Override
    public void onClick(View v)
    {
        bonky=MediaPlayer.create(this,R.raw.bonk);
        correct=MediaPlayer.create(this,R.raw.correct);
        switch (v.getId())
        {

            case R.id.imageButton3:
                correct.start();
                Toast.makeText(intro_quiz1.this,"Correct answer!",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new  Intent(intro_quiz1.this,question2.class));
                break;

            case R.id.imageView3:
                bonky.start();
            Toast.makeText(intro_quiz1.this,"Wrong answer!",Toast.LENGTH_LONG).show();
            break;

            case R.id.imageView4:
                bonky.start();
                Toast.makeText(intro_quiz1.this,"Wrong answer!",Toast.LENGTH_LONG).show();
                break;

            case R.id.imageView7:
                bonky.start();
                Toast.makeText(intro_quiz1.this,"Wrong answer!",Toast.LENGTH_LONG).show();
                break;
                //add counter

        }

    }


}