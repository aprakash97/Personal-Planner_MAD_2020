package com.example.personalplanner.Timer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.personalplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewTime extends AppCompatActivity {
    private Button backButton;
    private SwipeMenuListView listViews;
    private ArrayList<StopWatch> dataArrayList;
    private TimeListAdapter listAdapter;
    private FirebaseFirestore db ;
    private String m_Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity_view_time);
        db = FirebaseFirestore.getInstance();
        backButton=findViewById(R.id.back_btn);
        System.out.println("firebase");
        listViews = (SwipeMenuListView) findViewById(R.id.view_time_details);
        dataArrayList = new ArrayList<>();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewTime.this,StopWatchScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        retrive_timer_details();
    }

    private void retrive_timer_details(){

        getAllData();
        listAdapter = new TimeListAdapter(ViewTime.this, dataArrayList);
        listViews.setAdapter(listAdapter);

        listViews.setMenuCreator(creator);
        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

                final StopWatch curr_watch=dataArrayList.get(i);

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewTime.this);
                builder.setTitle("Update Name");

                final EditText input = new EditText(ViewTime.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(curr_watch.getName());
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        DocumentReference contact = db.collection("stopwatches").document(curr_watch.getId());

                        contact.update("name",  m_Text)
                                .addOnSuccessListener(new OnSuccessListener <Void> () {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ViewTime.this, "Updated Successfully",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(ViewTime.this,ViewTime.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                    }
                                });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                builder.show();

            }
        });
        listViews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        delete_cur_customer(position);
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
        db.collection("stopwatches")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                StopWatch stopWatch=new StopWatch();
                                stopWatch.setId(document.getId());
                                stopWatch.setName(document.get("name").toString());
                                stopWatch.setTime(document.get("time").toString());
                                dataArrayList.add(stopWatch);
                            }
                            listAdapter = new TimeListAdapter(ViewTime.this, dataArrayList);
                            listViews.setAdapter(listAdapter);
                            listViews.setMenuCreator(creator);
                        } else {
                            Toast.makeText(ViewTime.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void delete_cur_customer(int position){
        StopWatch stopWatch=new StopWatch();
        stopWatch=dataArrayList.get(position);
        db.collection("stopwatches").document(stopWatch.getId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ViewTime.this, "Sucessfully Deleted",
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
            deleteItem.setIcon(R.drawable.timer_delete_icon);
            deleteItem.setTitleColor(Color.WHITE);

            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}