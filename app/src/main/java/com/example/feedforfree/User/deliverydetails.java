package com.example.feedforfree.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feedforfree.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class deliverydetails extends AppCompatActivity {
    String title;
    EditText donorphoneNo,donorpickupaddress,donorfItem,donorFname,donorpreferedtime,donorpickupday,donorquantity;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    Button pickbtn,backbutton;
    String userId;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_deliverydetails);
        getSupportActionBar().setTitle("Food PickUp details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        donorphoneNo=findViewById(R.id.DonorPhoneNo);
        donorpickupaddress=findViewById(R.id.donorpickUpaddress);
        donorfItem=findViewById(R.id.donorFItem);
        donorFname=findViewById(R.id.donorFname);
        donorpreferedtime=findViewById(R.id.donorpreferedtime);
        donorpickupday=findViewById(R.id.donorpickupday);
        donorquantity=findViewById(R.id.donorquantity);
        pickbtn = findViewById(R.id.pickupbtn);
        backbutton = findViewById(R.id.button3);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                finish();
            }
        });




        pickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = donorFname.getText().toString();
                String phoneNo = donorphoneNo.getText().toString();
                String pickupaddress = donorpickupaddress.getText().toString();
                String fooditem = donorfItem.getText().toString();
                String peferedtime = donorpreferedtime.getText().toString();
                String pickupday = donorpickupday.getText().toString();
                String quantity = donorquantity.getText().toString();

                if(fullname.isEmpty()){
                    donorFname.setError("Required");
                    return;
                }

                if(pickupaddress.isEmpty()){
                    donorpickupaddress.setError("Required");
                    return;
                }

                if(phoneNo.isEmpty()){
                    donorphoneNo.setError("Required");
                    return;
                }

                if(peferedtime.isEmpty()){
                    donorpreferedtime.setError("Required");
                    return;
                }
                if(pickupday.isEmpty()){
                    donorpickupday.setError("Required");
                    return;
                }

                Toast.makeText(deliverydetails.this,"Data Validated",Toast.LENGTH_SHORT).show();
                userId = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = firestore.collection("Delivery").document(userId);


                Map<String,Object> delivery = new HashMap<>();
                delivery.put("fullname",fullname);
                delivery.put("phoneNo.",phoneNo);
                delivery.put("pickupaddress",pickupaddress);
                delivery.put("fooditems",fooditem);
                delivery.put("preferedtime",peferedtime);
                delivery.put("pickUpday",pickupday);
                delivery.put("quantitiy",quantity);

                documentReference.set(delivery).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(deliverydetails.this,"Pick Up details saved",Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(deliverydetails.this,"error not saved",Toast.LENGTH_SHORT).show();

                    }
                });
                startActivity(new Intent(getApplicationContext(),thankyou.class));
                finish();
            }

        });
    }

}