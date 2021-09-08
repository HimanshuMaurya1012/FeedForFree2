package com.example.feedforfree.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.feedforfree.R;
import com.example.feedforfree.User.Login;
import com.example.feedforfree.User.UserDashboard;

public class MainActivity extends AppCompatActivity {

    private static  int Splash_timer =5000;

    //hooks
    ImageView backgroundImage;
    //animations
    Animation sideAnim,bottomAnim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //hooks
        backgroundImage=findViewById(R.id.imageView2);

        //animation
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set animation on aliment
        backgroundImage.setAnimation(sideAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(MainActivity.this, Login.class);
                startActivity(intent);




            }
        },Splash_timer);



    }

}