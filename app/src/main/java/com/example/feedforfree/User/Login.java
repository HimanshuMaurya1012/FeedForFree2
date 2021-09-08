package com.example.feedforfree.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.DragStartHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feedforfree.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    Button createaccountbutton,loginbtn,forgotpassbtn;
    EditText username,password;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;

    public static void logout() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();


        createaccountbutton = findViewById(R.id.logincreateaccnt);
        createaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });

        username = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        loginbtn = findViewById(R.id.loginbtn);
        forgotpassbtn = findViewById(R.id.forgotbutton);

        forgotpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the error dialog
                View view = inflater.inflate(R.layout.reset_pop,null);

                reset_alert.setTitle("Reset Forgot password ?")
                        .setMessage("Enter your Email to get Password Reset link")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //validate the email address
                                EditText email = view.findViewById(R.id.emailpopup);
                                if (email.getText().toString().isEmpty()){
                                    email.setError("Required Field");
                                    return;
                                }
                                //send the reset link
                                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Login.this,"Reset Email Sent",Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        }).setNegativeButton("Cancel",null)
                        .setView(view)
                        .create().show();

            }
        });
        //login button

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //extract
              if (username.getText().toString().isEmpty()){
                  username.setError("Email missing");
                  return;
              }
              if (password.getText().toString().isEmpty()){
                  password.setError("Password missing");
                  return;

              }
              //data is valid

              //login the user
                firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //ogin is successful
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(),UserDashboard.class));
            finish();

        }
    }
}