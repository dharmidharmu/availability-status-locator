package com.example.atatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
    public void pub(View v) {
        Intent i = new Intent(view.this, retrive.class);
        startActivity(i);
    }
    public void pri(View v) {
        Intent i = new Intent(view.this, retrivee.class);
        startActivity(i);
    }
}
