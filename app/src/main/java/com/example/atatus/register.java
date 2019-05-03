package com.example.atatus;

import android.Manifest;
import android.app.NotificationManager;
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

public class register extends AppCompatActivity implements LocationListener {
    public static final String userDetails = "com.example.atatus";
    private EditText
            user, email, pass, name, age, mobile, profession, office;
    private EditText lati, longi;
    private LocationManager locationManager;

    private Button submit;
    Double latiid, longiid;
    String latitude,longitude;


    public register() {
    }

    public boolean isValid(String s) {
        return (!s.trim().isEmpty());
    }


    public boolean passIsValid(String s) {
        return (s.length() >= 5 && isValid(s));
    }


    public int C2I(String st) {
        return Integer.parseInt(st);
    }

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        user = (EditText) findViewById(R.id.user);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        mobile = (EditText) findViewById(R.id.mobile);
        profession = (EditText) findViewById(R.id.prof);
        office = (EditText) findViewById(R.id.off);
        lati = (EditText) findViewById(R.id.lat);
        longi = (EditText) findViewById(R.id.longi);

        submit = (Button) findViewById(R.id.submit);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        onLocationChanged(location);


    }
    public void onclickregister (View v){
        register();

    }
    private void register ()
    {

        String userid = user.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String passid = pass.getText().toString().trim();
        String nameud = name.getText().toString().trim();
        String ageid = age.getText().toString().trim();
        String mobileid = mobile.getText().toString().trim();
        String profid = profession.getText().toString().trim();
        String offid = office.getText().toString().trim();
        latitude = lati.getText().toString().trim();
        longitude = longi.getText().toString().trim();
        if (TextUtils.isEmpty(user.getText().toString().trim()) &&
                TextUtils.isEmpty(email.getText().toString().trim()) && TextUtils.isEmpty(pass.getText().toString().trim())
                && TextUtils.isEmpty(name.getText().toString().trim()) && TextUtils.isEmpty(age.getText().toString().trim()) && TextUtils.isEmpty(mobile.getText().toString().trim())
                && TextUtils.isEmpty(profession.getText().toString().trim()) && TextUtils.isEmpty(office.getText().toString().trim()) && TextUtils.isEmpty(lati.getText().toString().trim()) && TextUtils.isEmpty(longi.getText().toString().trim())) {

            user.setError("Required");
            email.setError("Required");
            pass.setError("Required");
            name.setError("Required");
            age.setError("Required");
            mobile.setError("Required");
            profession.setError("Required");
            office.setError("Required");
        } else if (TextUtils.isEmpty(user.getText().toString().trim())) {
            user.setError("Enter Username");
        } else if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError("Required");
        } else if (!emailValidator(email.getText().toString())) {
            email.setError("Please Enter Valid Email Address");
        }
        else if (TextUtils.isEmpty(pass.getText().toString().trim())) {
            pass.setError("Required");
        }else if (TextUtils.isEmpty(name.getText().toString().trim())) {
            name.setError("Enter Username");}
        else if (TextUtils.isEmpty(age.getText().toString().trim())) {
            age.setError("Required");
        } else if (!ageValidator(age.getText().toString())) {
            age.setError("Age must be Between 0 to 100");
        }
        else if (TextUtils.isEmpty(mobile.getText().toString().trim())) {
            mobile.setError("Required");
        } else if (!phValidator(mobile.getText().toString())) {
            mobile.setError("Please Enter a Valid Mobile Number");
        }
        else if (TextUtils.isEmpty(profession.getText().toString().trim())) {
            user.setError("Enter your profession");
        }
        else if (TextUtils.isEmpty(user.getText().toString().trim())) {
            user.setError("Enter your office/Institute");
        }else {
            mAuth.createUserWithEmailAndPassword(emailid, passid)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userdata();
                                startActivity(new Intent(getApplicationContext(), login.class));
                                Clickme();
                                Toast.makeText(register.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(register.this, "Please Try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    private void Clickme() {
        NotificationCompat.Builder builder =(NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("REGISTRATION SUCCESSFUL!!!!")
                .setContentText("Your Registration on Railway Empire Application is Successful");
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
    public static boolean emailValidator(String email)
    {
        return(!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private static boolean phValidator(String phone){
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN="^[2-9]{2}[0-9]{8}$";
        pattern=Pattern.compile(PHONE_PATTERN);
        matcher=pattern.matcher(phone);
        return matcher.matches();
    }

    private static boolean ageValidator(String age){
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN="^[0-9]{2}$";
        pattern=Pattern.compile(PHONE_PATTERN);
        matcher=pattern.matcher(age);
        return matcher.matches();
    }

    public void userdata(){

        String userid = user.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String passid = pass.getText().toString().trim();
        String nameid = name.getText().toString().trim();
        String ageid = age.getText().toString().trim();
        String mobileid = mobile.getText().toString().trim();
        String profid = profession.getText().toString().trim();
        String offid = office.getText().toString().trim();

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference register = firebaseDatabase.getReference().child("users").child("public").child(mAuth.getUid());
        users reg = new users(userid, emailid, passid, nameid, ageid, mobileid, profid, offid);
        register.setValue(reg);
        setvalue();

    }
    public  void setvalue(){
        //String latiid = lati.getText().toString().trim();
        //String longiid = longi.getText().toString().trim();
        latiid =Double.parseDouble(latitude);
        longiid=Double.parseDouble(longitude);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference locat;
        locat =firebaseDatabase.getReference().child("location").child(mAuth.getUid()).child("Location");
        String addid=locat.push().getKey();
        setting register=new setting(addid,latiid,longiid);
        locat.child(addid).setValue(register);

    }


    @Override
    public void onLocationChanged(Location location) {
        double longitude=location.getLongitude();
        double latitude=location.getLatitude();
        String str1=Double.toString(latitude);
        String str2=Double.toString(longitude);
        lati.setText(str1);
        longi.setText(str2);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


