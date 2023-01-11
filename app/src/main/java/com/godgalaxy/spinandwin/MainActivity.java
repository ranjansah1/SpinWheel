package com.godgalaxy.spinandwin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        auth= FirebaseAuth.getInstance();

        if (auth.getCurrentUser() !=null){

            startActivity(new Intent(MainActivity.this,SpinnerActivity.class));
            finish();
        }
        else {
          Thread();
        }





    }

    private void Thread(){
        Thread thread = new Thread(){

            @Override
            public void run() {
                try {

                    sleep(4000);
                }
                catch (Exception e){

                    e.getLocalizedMessage();
                }
                finally {

                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }



            }
        };thread.start();
    }
}