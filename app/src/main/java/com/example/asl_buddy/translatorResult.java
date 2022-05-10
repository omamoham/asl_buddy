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
            finish();
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
            message2.setText("ASL doesn't sign every word you would use in English! Try using only the most important ones.");
            message2.setTextColor(Color.WHITE);
            message2.setTextSize(24);
            message2.setPadding(100, 100, 100, 0);
            message2.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            message2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView message3 = new TextView(this);
            message3.setText("For example, rather than \n\"Where are you going?\", \ntry \n\"Where you go?\"");
            message3.setTextColor(Color.WHITE);
            message3.setTextSize(20);
            message3.setPadding(100, 100, 100, 100);
            message3.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            message3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            layoutY.addView(message);
            layoutY.addView(message2);
            layoutY.addView(message3);
        }
        //Display available words
        else {
            //Gather words that weren't found
            ArrayList<String> notFound = new ArrayList<String>();
            String[] words = original.split(" ");
            for (int i = 0; i < words.length; i++) {
                if (found.indexOf(words[i]) == -1) {
                    notFound.add(words[i]);
                }
            }

            // Build columns/rows
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

            //Add not found list to layoutY
            if (notFound.isEmpty() == false) {
                TextView title = new TextView(this);
                title.setText("The following words are not available:");
                title.setTextColor(Color.WHITE);
                title.setTextSize(22);
                title.setPadding(50, 50, 100, 50);
                title.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                layoutY.addView(title);

                for (int i = 0; i < notFound.size(); i++) {
                    TextView notFoundWord = new TextView(this);
                    notFoundWord.setText(notFound.get(i));
                    notFoundWord.setTextColor(Color.WHITE);
                    notFoundWord.setTextSize(20);
                    notFoundWord.setPadding(100, 10, 100, 10);
                    notFoundWord.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                    layoutY.addView(notFoundWord);
                }

                TextView title2 = new TextView(this);
                title2.setText("\nMake sure to use basic, unconjugated words!");
                title2.setTextColor(Color.WHITE);
                title2.setTextSize(22);
                title2.setPadding(50, 20, 100, 50);
                title2.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                layoutY.addView(title2);
            }

        }

        // HOW TO vvv------------
        //ImageButton btn = new ImageButton(this);
        //btn.setImageResource(R.drawable.hello123);
        //layout.addView(btn);
        // HOW TO ^^^------------
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}