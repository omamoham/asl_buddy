package com.example.asl_buddy;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class home_page extends AppCompatActivity implements View.OnClickListener{  //test

    private ImageButton vocabu;
    private Button translate;
    private Button button_sign_out;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        //Vocab button
        vocabu=findViewById(R.id.imageButton);
        vocabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vocabu_intent=new Intent(getApplicationContext(),vocab.class);
                startActivity(vocabu_intent);
            }
        });

        //Translate button
        translate = findViewById(R.id.translateButton);
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent translate_intent = new Intent(getApplicationContext(), translator.class);
                startActivity(translate_intent);
            }
        });

        button_sign_out=findViewById(R.id.button10);
        button_sign_out.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // ...
            case R.id.button10:
                signOut();
                //startActivity(new Intent(home_page.this,MainActivity.class));
                break;
            }
        //break;
            // ...
        }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(home_page.this,"Signed Out",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(home_page.this,MainActivity.class));
                    }
                });
    }



}