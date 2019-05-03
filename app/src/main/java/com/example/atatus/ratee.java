package com.example.atatus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class ratee extends AppCompatActivity {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Button btn2;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Inflate the layout for this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratee);

        btn2 = (Button) findViewById(R.id.ratebtn);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                rate();
            }
        });

    }
    public void rate() {
        float ratingvalue = ratingBar.getRating();
        Toast.makeText(ratee.this, "Rating is" + ratingvalue, Toast.LENGTH_SHORT).show();
    }




}

