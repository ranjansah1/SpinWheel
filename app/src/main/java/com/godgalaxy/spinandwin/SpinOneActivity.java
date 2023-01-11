package com.godgalaxy.spinandwin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class SpinOneActivity extends AppCompatActivity {

   private    Button btnSpin;
   private   ImageView ivWheel;
    private  CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_one);
        getSupportActionBar().hide();
        btnSpin= findViewById(R.id.btnSpin);
        ivWheel= findViewById(R.id.ivWheel);


        Random random= new Random();

        btnSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              btnSpin.setEnabled(false);


              int spin= random.nextInt(20)+10;

              spin= spin* 36;


              timer= new CountDownTimer( spin *20,1) {
                  @Override
                  public void onTick(long l) {

                      float rotatoin= ivWheel.getRotation()+2;

                      ivWheel.setRotation(rotatoin);

                  }

                  @Override
                  public void onFinish() {

                      btnSpin.setEnabled(true);
                  }
              }.start();

            }
        });

    }
}