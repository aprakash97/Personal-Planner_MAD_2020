package com.example.personalplanner.Alarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.personalplanner.Alarm.AlarmClock;
import com.example.personalplanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateAlarm extends AppCompatActivity {
    private AlarmClock clock;
    private TimePicker timePicker;
    private boolean isSnooze;
    private boolean isRepeat;
    private SwitchCompat repeat_switch,snooze_repeat;
    private Button btnAdd,btnCancel;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_update_alarm);
        clock=(AlarmClock) getIntent().getSerializableExtra("AlarmClass");
        db=FirebaseFirestore.getInstance();
        timePicker=findViewById(R.id.timePicker_update);
        repeat_switch=findViewById(R.id.repeatAlarm_update);
        snooze_repeat=findViewById(R.id.snooze_update);
        repeat_switch=findViewById(R.id.repeatAlarm_update);
        snooze_repeat=findViewById(R.id.snooze_update);
        btnAdd=findViewById(R.id.btnSubmit_update);
        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateAlarm.this,ViewAlarm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }

        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                db.collection("alarms").document(clock.getId())
                        .update(
                                "alarm_time", timer,
                                "isRepeat", repeat_switch.isChecked(),
                                "isSnooze",snooze_repeat.isChecked()

                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateAlarm.this,"Update Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UpdateAlarm.this,ViewAlarm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateAlarm.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UpdateAlarm.this,ViewAlarm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                });
            }
        });

        repeat_switch.setChecked(clock.isRepeat());
        snooze_repeat.setChecked(clock.isSnooze());
        String str=clock.getTime();
        String[] arrOfStr = str.split(":", 0);
        if(arrOfStr[1].split(" ",0)[1].equals("PM")){
            timePicker.setHour(Integer.parseInt(arrOfStr[0])+12);
        }else{
            timePicker.setHour(Integer.parseInt(arrOfStr[0]));
        }
        timePicker.setMinute(Integer.parseInt(arrOfStr[1].split(" ",0)[0]));
    }
}