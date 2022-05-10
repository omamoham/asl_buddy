package com.example.asl_buddy;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.content.Context;

public class Vocabulary {
    private ArrayList<String> vocab; //Available to use. Can be used for vocab builder
    private String availableWords; //Used in the lookup function. Don't mess with these

    public Vocabulary(Context context) {
        vocab = new ArrayList<String>();

        //Get available words from assets/availableWords.txt
        availableWords = "";
        try {
            InputStream is = context.getAssets().open("availableWords.txt");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            availableWords = new String(buffer);
        } catch (IOException e) {
            availableWords = "ERROR";
            e.printStackTrace();
        }

        String[] words = availableWords.split("\n");

        //Store individual words into ArrayList
        for (int i = 0; i < words.length; i++) {
            vocab.add(words[i]);
        }
    }

    //Takes a sentence as input (lowercase, no punctuation). Returns list of available words
    public ArrayList<String> lookup(String sentence) {
        ArrayList<String> found = new ArrayList<String>();

        //Break input into individual words to use to search
        String[] words = sentence.split(" ");

        for (int i = 0; i < words.length; i++) {
            //If available
            //words[i].equals(found.get(i))
            //availableWords.indexOf(words[i]) != -1
            if (isValid(words[i])) {
                System.out.println(words[i]);
                //Are there multiple?
                found.add(words[i]);

                int num = 1;
                Boolean multiple = true;

                while (multiple) {
                    String next = words[i] + Integer.toString(num);

                    //If there are multiple
                    if (isValid(next)) {
                        found.add(next);
                        num++;
                    }
                    else {
                        multiple = false;
                    }
                }
            }
        }

        return found;
    }

    public ArrayList<String> getVocab() {
        return vocab;
    }

    private Boolean isValid(String word1) {
        String[] word2 = availableWords.split("\n");

        //Loop through every available word (word2[i]), test chars of each
        for (int i = 0; i < word2.length; i++) {
            if (sameChars(word1, word2[i]))
                return true;
        }

        return false;
    }

    private Boolean sameChars(String word1, String word2) {
        System.out.println(word1 + " == " + word2 + "?");
        System.out.println(word1.length() + " " + word2.length());
        if (word1.length() != word2.length() - 1)
            return false;
        else {
            for (int k = 0; k < word1.length(); k++) {
                System.out.println(word1.charAt(k) + " == " + word2.charAt(k) + "?");
                if (word1.charAt(k) != word2.charAt(k)) {
                    System.out.println("no");
                    return false;
                }
            }
        }

        return true;
    }
}