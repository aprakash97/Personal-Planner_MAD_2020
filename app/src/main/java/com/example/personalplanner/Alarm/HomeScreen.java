package com.example.personalplanner.Alarm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.personalplanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {
    private TimePicker timePicker;
    private boolean isSnooze;
    private boolean isRepeat;
    private SwitchCompat repeat_switch,snooze_repeat;
    private Button btnAdd,btnView;
    private FirebaseFirestore db;
    public HomeScreen() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_home_screen);
        db=FirebaseFirestore.getInstance();
        timePicker=findViewById(R.id.timePicker);
        repeat_switch=findViewById(R.id.repeatAlarm);
        snooze_repeat=findViewById(R.id.snooze);
        repeat_switch=findViewById(R.id.repeatAlarm);
        snooze_repeat=findViewById(R.id.snooze);
        btnAdd=findViewById(R.id.btnSubmit);
        btnView=findViewById(R.id.btnView);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeScreen.this,ViewAlarm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        repeat_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRepeat=b;
            }
        });
        snooze_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSnooze=b;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                btnAdd.setEnabled(false);
                String timer="";
                int hrs=timePicker.getHour();
                int min=timePicker.getMinute();
                String am_pm = "";
                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, hrs);
                datetime.set(Calendar.MINUTE, min);
                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":Integer.toString( datetime.get(Calendar.HOUR) );
                timer=strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm;
                addData(timer);
            }
        });


    }

    private void addData(String timer){
        Map<String, Object> user = new HashMap<>();
        user.put("alarm_time", timer);
        user.put("isSnooze", isSnooze);
        user.put("isRepeat", isRepeat);

// Add a new document with a generated ID
        db.collection("alarms")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        btnAdd.setEnabled(true);
                        Toast.makeText(HomeScreen.this,"Success",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        btnAdd.setEnabled(true);
                        Toast.makeText(HomeScreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}