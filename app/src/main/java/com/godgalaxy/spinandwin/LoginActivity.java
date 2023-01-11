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

public class LoginActivity extends AppCompatActivity {

    private Animation animation;

    TextView DontAcc,loginTb;
    private EditText Emailtb,passTb;

    private ProgressDialog LoginDilaog;
    private FirebaseFirestore LoginStore;
    private FirebaseAuth LoginAuth;
    ConstraintLayout LoginContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        DontAcc= findViewById(R.id.DontAcc);
        LoginContainer= findViewById(R.id.loginCointainer);
        Emailtb= findViewById(R.id.EmailTb);
        passTb= findViewById(R.id.PassTb);
        loginTb= findViewById(R.id.LoginBtn);

        AnimationLAyout();

        LoginDilaog= new ProgressDialog(this);
        LoginDilaog.setMessage("Loading...");

        LoginAuth= FirebaseAuth.getInstance();

        loginTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email,pass;
                email= Emailtb.getText().toString();
                pass= passTb.getText().toString();

                LoginDilaog.show();

                LoginAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            LoginDilaog.dismiss();

                            startActivity(new Intent(LoginActivity.this,SpinnerActivity.class));
                            finish();

                        }

                        LoginDilaog.dismiss();
                      //  Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        DontAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,SinUPActivity.class));
            }
        });

    }


    private void AnimationLAyout(){

        LoginContainer.setY(-1000);
        animation= AnimationUtils.loadAnimation(this,R.anim.left_slide);
         LoginContainer.setY(0);
        LoginContainer.startAnimation(animation);

    }
}