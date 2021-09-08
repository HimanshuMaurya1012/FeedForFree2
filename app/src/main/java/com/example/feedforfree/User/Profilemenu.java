package com.example.feedforfree.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedforfree.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profilemenu extends AppCompatActivity {
    TextView fullname,email,phoneNo;
    DrawerLayout drawerLayout;
    Button logout;


    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String  userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profilemenu);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout =findViewById(R.id.drawerlayout);
        phoneNo = findViewById(R.id.profilePhoneNo);
        logout=findViewById(R.id.logoutbtn);
        email = findViewById(R.id.profileEmailAddress);
        fullname = findViewById(R.id.profileFullName);
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        userid =auth.getCurrentUser().getUid();


        DocumentReference documentReference = firestore.collection("Users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                phoneNo.setText(documentSnapshot.getString("phoneNo"));
                email.setText(documentSnapshot.getString("email"));
                fullname.setText(documentSnapshot.getString("fname"));

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });






    }
    public void ClickMenu(View view){
        UserDashboard.openDrawer(drawerLayout);
    }
    public  void ClikLogo(View view){
        //close drawer
        UserDashboard.closeDrawer(drawerLayout);
    }
    public void clikhome(View view){
        // redirect to home
        UserDashboard.redirectActivity(this,UserDashboard.class);
    }
    public void ClickDashboard(View view){
        //recreate activity
        recreate();;
    }
    public void ClickaboutUs(View view)
    {
        UserDashboard.redirectActivity(this,Favourite.class);
    }
    public void ClickLogout(View view){
        //close app
        UserDashboard.logout(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        UserDashboard.closeDrawer(drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_name,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.resetpassword){
            startActivity(new Intent(getApplicationContext(),Resetpassword.class));
        }

        if (item.getItemId()== R.id.contactus){
            startActivity(new Intent(getApplicationContext(),ContactUs.class));
        }
        return super.onOptionsItemSelected(item);
    }
}