package com.example.personalplanner.Alarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.personalplanner.Alarm.AlarmListAdapter;
import com.example.personalplanner.Alarm.AlarmClock;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.personalplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewAlarm extends AppCompatActivity {
    private Button backButton;
    private SwipeMenuListView listViews;
    private ArrayList<AlarmClock> dataArrayList;
    private AlarmListAdapter listAdapter;
    private FirebaseFirestore db ;
    private  String m_Text;
    private ImageView addImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity_view_alarm);
        addImg=findViewById(R.id.addBtn);
        db = FirebaseFirestore.getInstance();
        backButton=findViewById(R.id.back_btn);
        listViews = (SwipeMenuListView) findViewById(R.id.view_alarm_details);
        dataArrayList = new ArrayList<>();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewAlarm.this,HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewAlarm.this,HomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        retrive_timer_details();
    }
    private void retrive_timer_details(){

        getAllData();
        listAdapter = new AlarmListAdapter(ViewAlarm.this, dataArrayList);
        listViews.setAdapter(listAdapter);

        listViews.setMenuCreator(creator);
        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                final AlarmClock clock=dataArrayList.get(i);
                Intent intent=new Intent(ViewAlarm.this, com.example.personalplanner.Alarm.UpdateAlarm.class);
                intent.putExtra("AlarmClass", clock);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
        listViews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        delete_cur_alarm(position);
                        dataArrayList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });

    }

    private void getAllData(){
        db.collection("alarms")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AlarmClock clock=new AlarmClock();
                                clock.setId(document.getId());
                                clock.setRepeat((Boolean)document.get("isRepeat"));
                                clock.setTime(document.get("alarm_time").toString());
                                clock.setSnooze((Boolean)document.get("isSnooze"));
                                dataArrayList.add(clock);
                            }
                            listAdapter = new AlarmListAdapter(ViewAlarm.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                            listViews.setMenuCreator(creator);
                        } else {
                            Toast.makeText(ViewAlarm.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void delete_cur_alarm(int position){
        AlarmClock clock;
        clock=dataArrayList.get(position);
        db.collection("alarms").document(clock.getId())
                .delete().addOnSuccessListener(new OnSuccessListener< Void >() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ViewAlarm.this, "Sucessfully Deleted",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.parseColor("#F45557")));
            // set item width
            deleteItem.setWidth(150);
            deleteItem.setIcon(R.drawable.alarm_delete_icon);
            deleteItem.setTitleColor(Color.WHITE);

            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}