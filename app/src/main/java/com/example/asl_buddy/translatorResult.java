package com.example.asl_buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Locale;
import java.util.ArrayList;

public class translatorResult extends AppCompatActivity {

    private String engText;
    private LinearLayout layoutY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator_result);

        engText = (String) getIntent().getStringExtra("data");

        //Convert to lowercase
        engText = engText.toLowerCase(Locale.ROOT);

        //Remove punctuation (,.!?)
        String input = new String("");
        for (int i = 0; i < engText.length(); i++) {
            char ch = engText.charAt(i);
            if (ch != '.' && ch != ',' && ch != '!' && ch != '?')
                input += ch;
        }

        //Make sure input isn't empty or whitespaces
        if (input == null || input.length() == 0 || input.trim().isEmpty()) {
            System.out.println("No input");
        }
        else {
            //Access vocab class
            Vocabulary vocab = new Vocabulary(this);
            //Lookup vocab entries
            ArrayList<String> found = vocab.lookup(input);

            //Send information to be displayed
            displayResults(found, input);
        }

    }

    private void displayResults(ArrayList<String> found, String original) {
        layoutY = findViewById(R.id.yLayout);

        //If nothing found
        if (found.isEmpty()) {
            System.out.println("SAY SORRY");
            //Say nothing found
            TextView message = new TextView(this);
            message.setText("Sorry! None of those words are in my vocabulary yet!");
            message.setTextColor(Color.WHITE);
            message.setTextSize(36);
            message.setPadding(100, 100, 100, 100);
            message.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView message2 = new TextView(this);
            String message2Text = "ASL doesn't sign every word you would use in English! Try using the most important ones.\n";
            message2Text += "For example, rather than \n\"Where are you going?\", \ntry \n\"Where you go?\"";
            message2.setText(message2Text);
            message2.setTextColor(Color.WHITE);
            message2.setTextSize(24);
            message2.setPadding(100, 100, 100, 100);
            message2.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            message2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            layoutY.addView(message);
            layoutY.addView(message2);
        }
        //Display available words
        else {
            int size = found.size();
            for (int i = 0; i < size; i++) {
                HorizontalScrollView scrollX = new HorizontalScrollView(this);
                LinearLayout layoutX = new LinearLayout(this);
                layoutX.setOrientation(LinearLayout.HORIZONTAL);

                scrollX.addView(layoutX);

                ArrayList<String> saved = new ArrayList<String>();
                String currentWord = found.get(i);
                saved.add(currentWord);

                //Check if there are multiple images for one word (i.e. "hello", "hello1")
                int numPictures = 1;
                if ((i + 1) < size) {
                    String next = currentWord + Integer.toString(numPictures);
                    Boolean searching = true;

                    while (searching) {
                        if ((i + 1) < size) {
                            if (next.equals(found.get(i + 1))) {
                                saved.add(next);
                                numPictures++;
                                i++;
                                next = currentWord + Integer.toString(numPictures);
                            }
                            else {
                                searching = false;
                            }
                        }
                        else {
                            searching = false;
                        }
                    }
                }

                //-- v Create rows v --

                //Make first letter capital in word to display
                String tmp = "" + currentWord.charAt(0);
                tmp = tmp.toUpperCase();
                String capWord = new String();
                if (currentWord.length() > 1) {
                    capWord = tmp + currentWord.substring(1);
                }
                else {
                    capWord = tmp;
                }

                //Add text to beginning
                TextView name = new TextView(this);
                name.setText(capWord);
                name.setTextColor(Color.WHITE);
                name.setTextSize(24);
                name.setPadding(100, 100, 100, 100);
                name.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                layoutX.addView(name);

                //Put pictures in row
                for (int k = 0; k < numPictures; k++) {
                    ImageButton symbol = new ImageButton(this);
                    symbol.setMaxHeight(800);
                    symbol.setMaxWidth(800);
                    symbol.setAdjustViewBounds(true);

                    String imageName = saved.get(k);
                    int drawableID = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    symbol.setImageResource(drawableID);

                    symbol.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent magnify = new Intent(getApplicationContext(), MagnifySymbol.class);
                            magnify.putExtra("drawID", drawableID); //Pass data to new activity
                            startActivity(magnify);
                        }
                    });

                    layoutX.addView(symbol);
                }
                //-- ^ Create rows ^ --

                layoutY.addView(scrollX);
            }

            //Display any words that were not found

        }

        // HOW TO vvv------------
        //ImageButton btn = new ImageButton(this);
        //btn.setImageResource(R.drawable.hello123);
        //layout.addView(btn);
        // HOW TO ^^^------------
    }
}