package com.example.atatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void log(View v) {
        Intent i = new Intent(MainActivity.this, login.class);
        startActivity(i);
    }
    public void reg(View v) {
        Intent i = new Intent(MainActivity.this, option.class);
        startActivity(i);
    }
    public void view(View v) {
        Intent i = new Intent(MainActivity.this, view.class);
        startActivity(i);
    }
}
