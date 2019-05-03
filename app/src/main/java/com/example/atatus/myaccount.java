package com.example.atatus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myaccount extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView place;
    private TextView mobile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        final TextView name=(TextView)findViewById(R.id.one);
        final TextView email=(TextView)findViewById(R.id.two);
        final TextView place=(TextView)findViewById(R.id.three);
        final TextView mobile=(TextView)findViewById(R.id.four);
        mAuth= FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child("public").child(mAuth.getUid());
        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("users").child("private").child(mAuth.getUid());
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            users data =dataSnapshot.getValue(users.class);
                            name.setText(data.getNameid());

                            email.setText(data.getEmailid());
                            place.setText(data.getOffid());
                            mobile.setText(data.getMobileid());

                        }
                        else {
                            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        userss data =dataSnapshot.getValue(userss.class);
                                        name.setText(data.getNameid());

                                        email.setText(data.getEmailid());
                                        place.setText(data.getOffid());
                                        mobile.setText(data.getMobileid());

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }





                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });



    }
}
