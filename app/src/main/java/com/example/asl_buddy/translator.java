package com.example.asl_buddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class translator extends AppCompatActivity {

    private Button useText;
    private EditText textInput;
    private ImageButton useVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        textInput = findViewById(R.id.inputText);

        //Use text option
        useText = findViewById(R.id.goButton);
        useText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textInput.getText().toString().trim();

                //Display results in new activity
                Intent result = new Intent(getApplicationContext(), translatorResult.class);
                result.putExtra("data", text); //Pass data to new activity
                startActivity(result);
            }
        });

        //Use speech to text
        useVoice = findViewById(R.id.micButton);
        useVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If don't have microphone permission, ask for permission
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Microphone permission not granted, requesting permission");
                    Toast.makeText(getApplicationContext(), "Please enable microphone permissions", Toast.LENGTH_LONG).show();

                    //Request permission
                    ActivityCompat.requestPermissions(translator.this,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            1);

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        //Permissions were granted, use it!
                        useMicrophone();
                    }
                }
                //Microphone permission is granted, use it!
                else {
                    useMicrophone();
                }
            }
        });
    }

    private void useMicrophone() {
        SpeechRecognition STT = new SpeechRecognition();
        String[] strings = {"Test", "1"};

        try {
            Toast.makeText(getApplicationContext(), "Listening...", Toast.LENGTH_LONG).show();

            //Get input from microphone
            String output = STT.initialize(strings);

            //Pass input to be processed (if recognized)
            if (output.equals("Speech could not be recognized.")) {
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
            }
            else {
                Intent result = new Intent(getApplicationContext(), translatorResult.class);
                result.putExtra("data", output); //Pass data to new activity
                startActivity(result);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}