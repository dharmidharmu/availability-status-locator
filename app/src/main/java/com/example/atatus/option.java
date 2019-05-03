package com.example.atatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }
    public void pub(View v) {
        Intent i = new Intent(option.this, register.class);
        startActivity(i);
    }
    public void pri(View v) {
        Intent i = new Intent(option.this, registerr.class);
        startActivity(i);
    }
}
