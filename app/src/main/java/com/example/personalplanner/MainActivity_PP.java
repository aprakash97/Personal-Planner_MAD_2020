package com.example.personalplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.personalplanner.Alarm.MainActivity;
import com.example.personalplanner.TODO_List.MainActivity_Todo;
import com.example.personalplanner.Timer.HomeScreen;

public class MainActivity_PP extends AppCompatActivity {

    Button todoNavigationBtn;
    Button timer;
    Button Alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_planner_activity_main);

        todoNav();
        timer();
        alarm();
    }

    private void alarm() {

        Alarm = (Button)findViewById(R.id.Time);
        Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarm = new Intent(MainActivity_PP.this, MainActivity.class);
                startActivity(alarm);
            }
        });
    }

    private void timer() {
        timer = (Button)findViewById(R.id.Reminder);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t =new Intent(MainActivity_PP.this, HomeScreen.class);
                startActivity(t);
            }
        });
    }

    private void todoNav() {

        todoNavigationBtn =(Button)findViewById(R.id.Todo);
        todoNavigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoNav = new Intent(MainActivity_PP.this, MainActivity_Todo.class);
                startActivity(todoNav);
            }
        });
    }
}