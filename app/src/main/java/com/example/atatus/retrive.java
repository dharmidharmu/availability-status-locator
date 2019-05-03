package com.example.atatus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;;import java.util.ArrayList;

public class retrive extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    users use;
    avail av;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive);

        mAuth = FirebaseAuth.getInstance();

        use=new users();
        av=new avail();
        listView  = (ListView) findViewById(R.id.listView);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("users").child("public");
        list=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.user_status,R.id.name,list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    use=ds.getValue(users.class);
                    av=ds.getValue(avail.class);
                    list.add(use.getNameid().toString()+"  "+use.getOffid()+"  "+av.getStatus());


                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
