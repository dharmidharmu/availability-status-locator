package com.example.atatus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class switchh extends AppCompatActivity {

    Switch simpleSwitch1;
    Button submit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switchh);
        // initiate view's
        simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);
        mAuth = FirebaseAuth.getInstance();

        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1;
                if (simpleSwitch1.isChecked()) {
                    statusSwitch1 = simpleSwitch1.getTextOn().toString();
                    String temp="Available";
                    userdata(temp);
                }
                else {
                    statusSwitch1 = simpleSwitch1.getTextOff().toString();
                    String temp1="Unavailable";
                    userdata(temp1);
                }

                Toast.makeText(getApplicationContext(), "Status:" + statusSwitch1 + "\n" , Toast.LENGTH_LONG).show(); // display the current state for switch's
            }
        });

    }

    public void userdata(final String temp) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child("public").child(mAuth.getUid());
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                            DatabaseReference register = firebaseDatabase.getReference().child("users").child("public").child(mAuth.getUid());
                            //avail reg = new avail(status);
                            register.child("status").setValue(temp);
                        }
                        else {
                            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                            DatabaseReference register = firebaseDatabase.getReference().child("users").child("private").child(mAuth.getUid());
                            //avail reg = new avail(status);
                            register.child("status").setValue(temp);

                        }





                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

   /* public void setpublic(String temp)
    {


        String status = temp;


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child("users").child(mAuth.getUid());
        //avail reg = new avail(status);
        register.child("status").setValue(status);


    }
    public void setprivate(String temp)
    {


        String status = temp;


        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child("users").child(mAuth.getUid());
        //avail reg = new avail(status);
        register.child("status").setValue(status);


    }*/
}
