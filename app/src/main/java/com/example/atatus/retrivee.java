package com.example.atatus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class retrivee extends AppCompatActivity {
    public static final String userDetails = "com.example.atatus";
    private EditText
            profpass;
    private Button submit;
    public retrivee(){}
    private FirebaseAuth mAuth;
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    userss use;
    avail av;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrivee);
        mAuth = FirebaseAuth.getInstance();
        use=new userss();
        av=new avail();
        listView  = (ListView) findViewById(R.id.listView);

        profpass = (EditText) findViewById(R.id.profpass);
        submit = (Button) findViewById(R.id.submit);
    }
    public void show (View v){
        showw();

    }
    public void showw(){
        final String password = profpass.getText().toString().trim();
        if (TextUtils.isEmpty(profpass.getText().toString().trim())) {
            profpass.setError("Enter the password");
        }
        else
        {
            database=FirebaseDatabase.getInstance();
            ref=database.getReference().child("users").child("private");
            list=new ArrayList<>();
            adapter=new ArrayAdapter<String>(this,R.layout.user_statuss,R.id.name,list);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        use = ds.getValue(userss.class);
                        av = ds.getValue(avail.class);
                        String temp = use.getProfpassid();
                        if (temp.equals(password)) {
                            list.add(use.getNameid().toString() + "  " + use.getOffid() + "  " + av.getStatus());


                       }
                    }
                    if(list.isEmpty())
                    {
                        list.add("Officers not found!");
                        Toast.makeText(retrivee.this, "Officers not found!", Toast.LENGTH_SHORT).show();

                    }
                    listView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        }
    }

