package com.godgalaxy.spinandwin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.godgalaxy.spinandwin.SpinWheel.LuckyWheelView;
import com.godgalaxy.spinandwin.SpinWheel.model.LuckyItem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

  //  ActivitySpinnerBinding binding;

    private LuckyWheelView luckyWheelView;
    private ImageView SpinBtn, Mute,BackBtn;

    private TextView CoinSHowtb,SpinCounttb,BackTime;
    private  String date1,date2,d1,d2;
    private Calendar calendar ,calendar2;
    SimpleDateFormat simpleDateFormat;

    int Count=2;
    private Dialog dialog;
    user user1;

    FirebaseFirestore firestore;

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
      //  setContentView(binding.getRoot());
        setContentView(R.layout.activity_spinner);
        getSupportActionBar().hide();

        luckyWheelView= findViewById(R.id.wheelview);
        SpinBtn= findViewById(R.id.spinBtn);
        CoinSHowtb= findViewById(R.id.CoinSHowTb);
        SpinCounttb= findViewById(R.id.SpinCountTb);
        Mute= findViewById(R.id.MuteBtn);
        BackBtn= findViewById(R.id.SpinBackBtn);


        getDate();

        List<LuckyItem> data = new ArrayList<>();

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.topText = "5";
        luckyItem1.secondaryText = "COINS";
        luckyItem1.textColor = Color.parseColor("#212121");
        luckyItem1.color = Color.parseColor("#eceff1");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.topText = "10";
        luckyItem2.secondaryText = "COINS";
        luckyItem2.color = Color.parseColor("#00cf00");
        luckyItem2.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.topText = "15";
        luckyItem3.secondaryText = "COINS";
        luckyItem3.textColor = Color.parseColor("#212121");
        luckyItem3.color = Color.parseColor("#eceff1");
        data.add(luckyItem3);

        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.topText = "20";
        luckyItem4.secondaryText = "COINS";
        luckyItem4.color = Color.parseColor("#7f00d9");
        luckyItem4.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "25";
        luckyItem5.secondaryText = "COINS";
        luckyItem5.textColor = Color.parseColor("#212121");
        luckyItem5.color = Color.parseColor("#eceff1");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.topText = "30";
        luckyItem6.secondaryText = "COINS";
        luckyItem6.color = Color.parseColor("#dc0000");
        luckyItem6.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem6);

        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "35";
        luckyItem7.secondaryText = "COINS";
        luckyItem7.textColor = Color.parseColor("#212121");
        luckyItem7.color = Color.parseColor("#eceff1");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.topText = "0";
        luckyItem8.secondaryText = "COINS";
        luckyItem8.color = Color.parseColor("#008bff");
        luckyItem8.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem8);



        luckyWheelView.setData(data);
        luckyWheelView.setRound(5);

        SharedPreferences sh= getSharedPreferences("date2", MODE_PRIVATE);
        date2= sh.getString("date2","");
        

        SpinBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (Count ==0 ) {
                    calendar2 = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("dd");
                    date2 = simpleDateFormat.format(calendar2.getTime());

                    SharedPreferences sh= getSharedPreferences("date2", MODE_PRIVATE);
                    SharedPreferences.Editor editor2= sh.edit();
                    editor2.putString("date2",date2);
                    editor2.apply();




                }

                if (date1.equals(date2)){

                    Toast.makeText(SpinnerActivity.this, "Today Work Done!", Toast.LENGTH_SHORT).show();
                }
                else {
                    SpinBtn.setEnabled(false);
                    mediaPlayer=  MediaPlayer.create(SpinnerActivity.this,R.raw.music);
                    mediaPlayer.start();
                    Random r = new Random();
                    int randomNumber = r.nextInt(8);

                    luckyWheelView.startLuckyWheelWithTargetIndex(randomNumber);
                }





            }
        });

        if (date1.equals(date2)){

        SpinBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        Toast.makeText(SpinnerActivity.this, "Today Work Done!", Toast.LENGTH_SHORT).show();
        }

        });

        }
        else {

        }


        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCash(index);
                mediaPlayer.stop();
                SpinBtn.setEnabled(true);
                dialog.show();


            }
        });

        firestore= FirebaseFirestore.getInstance();
        firestore.collection("user").
                document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                       user1  = documentSnapshot.toObject(user.class);
                        CoinSHowtb.setText(String.valueOf(user1.getCoins()+" "));


                    }
                });

    }

    void updateCash(int index) {
        long cash = 0;
        switch (index) {
            case 0:
                cash = 5;

                break;
            case 1:
                cash = 10;

                break;
            case 2:
                cash = 15;

                break;
            case 3:
                cash = 20;

                break;
            case 4:
                cash = 25;

                break;
            case 5:
                cash = 30;

                break;
            case 6:
                cash = 35;

                break;
            case 7:
                cash = 0;

                break;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.congo_dialog);
        TextView dialogCoinBtn= dialog.findViewById(R.id.dialogGetBtn);
        TextView dilogCOinSHow= dialog.findViewById(R.id.dialodbalance);

        dialog.setCanceledOnTouchOutside(false);
       String s= String.valueOf(cash);
       dilogCOinSHow.setText(s);



        database
                .collection("user")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(cash)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText(SpinnerActivity.this, "Coins added in account.", Toast.LENGTH_SHORT).show();

                     //   dialog.show();


                       // finish();
                    }
                });


        dialogCoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count--;
                SpinCounttb.setText(Count+"");
                dialog.dismiss();



            }
        });


    }
    private void getDate() {

       calendar= Calendar.getInstance();
       simpleDateFormat= new SimpleDateFormat("dd");
       date1= simpleDateFormat.format(calendar.getTime());

        SharedPreferences sharedPreferences= getSharedPreferences("Date1",MODE_PRIVATE);
        SharedPreferences.Editor edit1= sharedPreferences.edit();
        edit1.putString("Date1",date1);
        edit1.commit();

        date1= sharedPreferences.getString("Date1","");




    }

    private BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            UpdteGUI(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

       // registerReceiver(broadcastReceiver,new IntentFilter(BroadCastServise.COUNTDOWN_BR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStop() {

        try {

            unregisterReceiver(broadcastReceiver);
        }
        catch (Exception e){

            e.getLocalizedMessage();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {


    super.onDestroy();

    }

    private void UpdteGUI(Intent intent){
        if (intent.getExtras() != null){

           // BackTime.setText();
        }
    }
}