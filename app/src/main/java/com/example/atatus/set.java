package com.example.atatus;

import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class set extends AppCompatActivity  {
    public static final String userDetails = "com.example.atatus";

    Button btn,btn1;
    EditText addlat, addlog;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    Double latiid, longiid;
    String lat,longi;
    private LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btn = (Button) findViewById(R.id.save);
        addlat = (EditText) findViewById(R.id.addlat);
        addlog = (EditText) findViewById(R.id.addlog);


        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);*/









    }

    public void save(View view) {
        add();
    }







    private void add() {
        lat=addlat.getText().toString().trim();
        longi = addlog.getText().toString().trim();

        if (TextUtils.isEmpty(addlat.getText().toString().trim()) && TextUtils.isEmpty(addlog.getText().toString().trim())) {
            addlat.setError("Required");
            addlog.setError("Required");
            Toast.makeText(this, "Please Enter the Details", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(addlat.getText().toString().trim())) {
            addlat.setError("Enter Latitude");
        } else if (TextUtils.isEmpty(addlog.getText().toString().trim())) {
            addlog.setError("Required");
        }
        else
        {
            progressDialog.setMessage("Setting Location....Please Wait");
            progressDialog.show();
            userdata();
            startActivity(new Intent(set.this,status.class));
            Toast.makeText(set.this, "ADDED SUCCESSFULLY!!!", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();

    }



    private void userdata(){
        latiid =Double.parseDouble(lat);
        longiid=Double.parseDouble(longi);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference locat;
        locat =firebaseDatabase.getReference().child("location").child(mAuth.getUid()).child("Location");
        String addid=locat.push().getKey();
        loc register=new loc(addid,latiid,longiid);
        locat.child(addid).setValue(register);


        Toast.makeText(this, "added !", Toast.LENGTH_SHORT).show();
    }

   /* @Override
    public void onLocationChanged(Location location) {
        double longitude=location.getLongitude();
        double latitude=location.getLatitude();
        String str1=Double.toString(latitude);
        String str2=Double.toString(longitude);
        addlat.setText(str1);
        addlog.setText(str2);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/
}
