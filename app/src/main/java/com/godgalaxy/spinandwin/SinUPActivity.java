package com.godgalaxy.spinandwin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SinUPActivity extends AppCompatActivity {

    private TextView haveAnAcc;
     private EditText LoginNameTb,LoginEmailTb,LoginPassTb;
     private TextView Signbtn;

     private FirebaseAuth SignUpAuth;
     private FirebaseFirestore database;

     private Animation anim;

     private ConstraintLayout SignUpContainer;

     private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_upactivity);
        getSupportActionBar().hide();

        haveAnAcc= findViewById(R.id.HaveAcc);
        SignUpContainer= findViewById(R.id.SignUpContainer);
        LoginNameTb= findViewById(R.id.LoginNameTb);
        LoginEmailTb= findViewById(R.id.LoginEmailTb);
        LoginPassTb= findViewById(R.id.LoginPassTb);
        Signbtn= findViewById(R.id.SignUPBtn);

        LayoutAnim();

        dialog= new ProgressDialog(this);
        dialog.setMessage("We're creating new account...");

        SignUpAuth= FirebaseAuth.getInstance();
        database= FirebaseFirestore.getInstance();


        Signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name,email,pass;

                name= LoginNameTb.getText().toString();
                email= LoginEmailTb.getText().toString();
                pass= LoginPassTb.getText().toString();
                final user user1= new user(email,pass,name);

                dialog.show();

                SignUpAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        String uid= task.getResult().getUser().getUid();
                        database.collection("user")
                                .document(uid)
                                .set(user1)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        if (task.isComplete()){
                                            dialog.dismiss();
                                            startActivity(new Intent(SinUPActivity.this,SpinnerActivity.class));
                                            finishAffinity();

                                        }
                                        else
                                        {
                                            dialog.dismiss();
                                            Toast.makeText(SinUPActivity.this,task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                    }
                });
            }
        });






        haveAnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SinUPActivity.this,LoginActivity.class));
            }
        });
    }

    private void LayoutAnim(){

        SignUpContainer.setX(-1000);
        anim= AnimationUtils.loadAnimation(this,R.anim.right_slide);
        SignUpContainer.setX(0);
        SignUpContainer.startAnimation(anim);

    }

}