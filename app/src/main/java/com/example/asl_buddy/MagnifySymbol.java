package com.example.asl_buddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MagnifySymbol extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnify_symbol);

        LinearLayout layout = findViewById(R.id.magnifyLayer);

        int drawableID = (int) getIntent().getIntExtra("drawID", 0);

        if (drawableID != 0) {
            ImageView image = new ImageView(this);
            image.setImageResource(drawableID);
            layout.addView(image);
        }
        else {
            System.out.println("Magnifier passed invalid drawableID");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}