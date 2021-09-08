package com.example.feedforfree.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.feedforfree.R;

public class UserDashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button donatebtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        drawerLayout = findViewById(R.id.drawerlayout);
        donatebtn =findViewById(R.id.donatefood);


        donatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),deliverydetails.class));
                finish();
            }
        });
    }
    public void ClickMenu(View view){
        //open drawer

        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public void ClikLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        // close drawer layout
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void clikhome(View view){
        //recreate activity
        recreate();
    }
    public void  ClickDashboard(View view){
        //redirect activity to dashboaard
        redirectActivity(this,Profilemenu.class);

    }

    public void ClickaboutUs(View view){
        //redirect activity
        redirectActivity(this,Favourite.class);

    }
    public void ClickLogout(View view){
        //close app
        logout(this);

    }

    public static void logout(Activity activity) {
        //intialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("EXIT");
        //set message
        builder .setMessage("Are you sure you want to Exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });
        // negativive no button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aClass) {

        //intialize intent
        Intent intent=new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }
}







