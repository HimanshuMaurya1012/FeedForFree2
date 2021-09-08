package com.example.feedforfree.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.feedforfree.R;

public class Favourite extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_favourite3);
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawerlayout);


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
        UserDashboard.redirectActivity(this,Profilemenu.class);
    }
    public void ClickaboutUs(View view){

        recreate();
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
}