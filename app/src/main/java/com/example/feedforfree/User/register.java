package com.example.feedforfree.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.feedforfree.R;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText registerfullname,registerusername,registeremail,registerphoneNo,registerpassword,registerCpassword;
    Button registeruserbtn,alreadyhaveaccountbtn;
    TextView signUp;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        signUp=(TextView)findViewById(R.id.signuppage);


        registerfullname = findViewById(R.id.signupFname);
        registerusername = findViewById(R.id.signupUsername);
        registeremail = findViewById(R.id.signupEmail);
        registerphoneNo= findViewById(R.id.signupPhoneNo);
        registerpassword = findViewById(R.id.signuppassword);
        registerCpassword = findViewById(R.id.signupCpassword);
        alreadyhaveaccountbtn=findViewById(R.id.signupalrdyhvacc);
        registeruserbtn = findViewById(R.id.signupregbtn);

        fAuth=FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        alreadyhaveaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });


        registeruserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the data

                String fullname =registerfullname.getText().toString();
                String username =registerusername.getText().toString();
                String email = registeremail.getText().toString();
                String phoneNo =registerphoneNo.getText().toString();
                String password = registerpassword.getText().toString();
                String confirmpassword = registerCpassword.getText().toString();


                //validate the data
                if(fullname.isEmpty()){
                    registerfullname.setError("Required");
                    return;
                }

                if(username.isEmpty()){
                    registerusername.setError("Required");
                    return;
                }

                if(email.isEmpty()){
                    registeremail.setError("Required");
                    return;
                }

                if(phoneNo.isEmpty()){
                    registerphoneNo.setError("Required");
                    return;
                }
                if(password.isEmpty()){
                    registerpassword.setError("Required");
                    return;
                }

                if(confirmpassword.isEmpty()){
                    registerCpassword.setError("Required");
                    return;
                }
                //to confirm password match or not

                if (!password.equals(confirmpassword)){
                    registerCpassword.setError("Password do not match");
                    return;
                }
                //data is validated
                //regiter the user
                Toast.makeText(register.this,"Data Validated",Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(register.this,"USER Created",Toast.LENGTH_SHORT).show();
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userId);

                            Map<String,Object> user = new HashMap<>();
                            user.put("fname",fullname);
                            user.put("uname",username);
                            user.put("email",email);
                            user.put("phoneNo",phoneNo);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user Profile is created for"+userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure:"+e.toString());

                                }
                            });


                            startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                            finish();


                        }
                        else {
                            Toast.makeText(register.this,"Error",Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
        });
    }
}