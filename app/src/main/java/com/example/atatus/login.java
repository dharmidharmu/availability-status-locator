package com.example.atatus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private ProgressDialog progressDialog;
    Button button3;
    EditText emailtxt, passwordtxt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        button3 = (Button) findViewById(R.id.buttonlog);
        emailtxt = (EditText) findViewById(R.id.email);
        passwordtxt = (EditText) findViewById(R.id.pass);
        progressDialog=new ProgressDialog(this);
    }
    public void onclicklogin(View v) {
        login();
    }

    private void login() {
        String emailid=emailtxt.getText().toString().trim();
        String passid=passwordtxt.getText().toString().trim();
        if (TextUtils.isEmpty(emailtxt.getText().toString().trim()) && TextUtils.isEmpty(passwordtxt.getText().toString().trim())) {
            emailtxt.setError("Required");
            passwordtxt.setError("Required");
        } else if (!emailValidator(emailtxt.getText().toString())) {
            emailtxt.setError("Please Enter Valid Email Address");
        }else if(TextUtils.isEmpty(passwordtxt.getText().toString().trim()))
        {
            passwordtxt.setError("Required");
        }
        else {
            mAuth.signInWithEmailAndPassword(emailid,passid)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                startActivity(new Intent(login.this,status.class));
                                Toast.makeText(login.this, "logged IN", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(login.this, "Please Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public static boolean emailValidator(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public void onclickl(View v) {
        Intent i = new Intent(login.this, option.class);
        startActivity(i);
    }
    public void forget(View v) {
        Intent i = new Intent(login.this, forget.class);
        startActivity(i);
    }

}
