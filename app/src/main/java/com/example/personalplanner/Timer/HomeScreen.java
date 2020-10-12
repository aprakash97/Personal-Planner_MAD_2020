package com.example.personalplanner.Timer;

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
import com.example.personalplanner.R.layout;

public class HomeScreen extends AppCompatActivity {
    private TextView tvSplash,tvSubSplash;
    private Button btnget;
    private Animation animation,animationText,animationSubText;
    private ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.timer_activity_home_screen);
        tvSplash=findViewById(R.id.tvSplash);
        tvSubSplash=findViewById(R.id.tvSubSplash);
        btnget=findViewById(R.id.btnget);
        ivSplash=findViewById(R.id.ivSplash);
        animation= AnimationUtils.loadAnimation(this,R.anim.alpha_to);
        animationText= AnimationUtils.loadAnimation(this,R.anim.alpha_text);
        animationSubText= AnimationUtils.loadAnimation(this,R.anim.alpha_sub_text);

        ivSplash.startAnimation(animation);
        tvSplash.startAnimation(animationText);
        tvSubSplash.startAnimation(animationSubText);
        btnget.startAnimation(animationSubText);

        Typeface MLight= Typeface.createFromAsset(getAssets(),"fonts/MLight.ttf");
        Typeface MMedium= Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");
        Typeface MRegular= Typeface.createFromAsset(getAssets(),"fonts/MRegular.ttf");

        tvSplash.setTypeface(MRegular);
        tvSubSplash.setTypeface(MLight);
        btnget.setTypeface(MMedium);

        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeScreen.this,StopWatchScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }
}