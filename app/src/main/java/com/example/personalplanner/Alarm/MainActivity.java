package com.example.personalplanner.Alarm;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.personalplanner.R;


public class MainActivity extends AppCompatActivity {
    private TextView txtHead,txtSubHead;
    private Button btnEnter;
    private Animation animation,animationText,animationSubText;
    private ImageView ivSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_main);
        txtHead=findViewById(R.id.alarmHead);
        txtSubHead=findViewById(R.id.alarmSubHead);
        ivSplash=findViewById(R.id.logoImage);
        animation= AnimationUtils.loadAnimation(this,R.anim.alarm_alpha_to);
        animationText= AnimationUtils.loadAnimation(this,R.anim.alarm_alpha_text);
        animationSubText= AnimationUtils.loadAnimation(this,R.anim.alarm_alpha_sub_text);
        btnEnter=findViewById(R.id.btnStart);
        Typeface MLight= Typeface.createFromAsset(getAssets(),"fonts/alarm_MLight.ttf");
        Typeface MMedium= Typeface.createFromAsset(getAssets(),"fonts/alarm_MMedium.ttf");
        Typeface MRegular= Typeface.createFromAsset(getAssets(),"fonts/alarm_MRegular.ttf");

        btnEnter.setTypeface(MMedium);
        txtHead.setTypeface(MRegular);
        txtSubHead.setTypeface(MLight);


        ivSplash.startAnimation(animation);
        txtHead.startAnimation(animationText);
        txtSubHead.startAnimation(animationSubText);
        btnEnter.startAnimation(animationSubText);

        btnEnter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }
}