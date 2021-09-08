package com.example.feedforfree.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feedforfree.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Resetpassword extends AppCompatActivity {

    EditText userpassword,confirmpassword;
    Button savepasswordbtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resetpassword);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userpassword = findViewById(R.id.newpasswrd);
        confirmpassword = findViewById(R.id.cnfrnpassword);

        user = FirebaseAuth.getInstance().getCurrentUser();

        savepasswordbtn = findViewById(R.id.resetsavbtn);
        savepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userpassword.getText().toString().isEmpty()){
                    userpassword.setError("Required Field");
                    return;
                }

                if (confirmpassword.getText().toString().isEmpty()){
                    confirmpassword.setError("Required Field");
                    return;
                }
                if (!userpassword.getText().toString().equals(confirmpassword.getText().toString())){
                    confirmpassword.setError("Password Do Not Matched");
                    return;
                }

                user.updatePassword(userpassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Resetpassword.this,"Password Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Resetpassword.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });



    }
}