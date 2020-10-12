package com.example.personalplanner.Timer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.personalplanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StopWatchScreen extends AppCompatActivity {
    private Button btnStart,btnReset,btnAdd,btnView;
    private ImageView icanchor;
    private Animation roundingAlone;
    private long pauseOffset;
    private Chronometer timeHere;
    private boolean running;
   private FirebaseFirestore db ;
    private String m_Text;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity_stop_watch_screen);
        db = FirebaseFirestore.getInstance();
        btnStart=findViewById((R.id.btnStart));
        icanchor=findViewById(R.id.icanchor);
        timeHere=findViewById(R.id.timerHere);
        btnReset=findViewById(R.id.btnReset);
        btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setVisibility(View.INVISIBLE);
        btnView=findViewById(R.id.btnView);
        Typeface MMedium= Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");
        btnStart.setTypeface(MMedium);
        roundingAlone= AnimationUtils.loadAnimation(this,R.anim.rounding_alone);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running){
                    icanchor.startAnimation(roundingAlone);
                    timeHere.setBase(SystemClock.elapsedRealtime()-pauseOffset);
                    timeHere.start();
                    btnAdd.setVisibility(View.INVISIBLE);
                    running=true;
                    btnStart.setText("Stop");
                    btnStart.setBackground(ContextCompat.getDrawable(StopWatchScreen.this,R.drawable.timer_btn_redbackground));
                }else{
                    timeHere.stop();
                    pauseOffset= SystemClock.elapsedRealtime()-timeHere.getBase();
                    icanchor.clearAnimation();
                    btnAdd.setVisibility(View.VISIBLE);
                    running=false;
                    btnStart.setText("Start");
                    btnStart.setBackground(ContextCompat.getDrawable(StopWatchScreen.this,R.drawable.timer_button_background));
                }

            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int seconds;
                final int minutes;

                long Mills =pauseOffset;
                minutes = (int)(Mills / 1000)  / 60;
                seconds = (int)( Math.floor(Mills / 1000) % 60);
                System.out.println("time"+minutes+":"+seconds);

                builder = new AlertDialog.Builder(StopWatchScreen.this);
                builder.setTitle("Please Enter a name");

                final EditText input = new EditText(StopWatchScreen.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        resetTimer();
                        addData(minutes,seconds);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        resetTimer();
                    }
                });

                builder.show();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StopWatchScreen.this,ViewTime.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }

    private void resetTimer(){
        icanchor.clearAnimation();
        btnStart.animate().alpha(1).translationY(-80).setDuration(300).start();
        pauseOffset=0;
        timeHere.setBase(SystemClock.elapsedRealtime());
        btnAdd.setVisibility(View.INVISIBLE);
    }

    private void addData(int min,int sec){
        Map<String, Object> timer=new HashMap<>();
        timer.put("name",m_Text);
        timer.put("time",min+" minutes & "+sec +" seconds");
        db.collection("stopwatches").add(timer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       Toast.makeText(StopWatchScreen.this,"Sucessfully Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StopWatchScreen.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}