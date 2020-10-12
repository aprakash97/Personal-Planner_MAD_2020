package com.example.personalplanner.TODO_List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskAct extends AppCompatActivity {

    //Initialize Variable
    TextView titlepage;
    TextView addtitle;
    TextView adddesc;
    TextView adddate;

    EditText titletodo;
    EditText desctodo;
    EditText datetodo;

    Button btnSaveTask;
    Button btnCancel;

    DatabaseReference reference;
    Integer todoNum = new Random().nextInt();
    String keytodo = Integer.toString(todoNum);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_new_task);

     saveButton();
     cancelButton();
}

    private void saveButton()   // For Save Data to ======================>> Firebase
    {

        titlepage = (TextView)findViewById(R.id.titlepage);

        addtitle = (TextView)findViewById(R.id.addtitle);
        adddesc = (TextView)findViewById(R.id.adddesc);
        adddate = (TextView)findViewById(R.id.adddate);

        titletodo = (EditText)findViewById(R.id.titletodo);
        desctodo = (EditText)findViewById(R.id.desctodo);
        datetodo =(EditText)findViewById(R.id.datetodo);

        btnSaveTask = (Button)findViewById(R.id.btnSaveTask);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Validation
                if (titletodo.length() == 0){
                    titletodo.setError("Title Filed Can't be Empty");
                }else if (desctodo.length() == 0){
                    desctodo.setError("Description Can't be Empty");
                }else if (datetodo.length() == 0){
                    datetodo.setError("Timeline Can't be Empty");
                }else{

                    reference = FirebaseDatabase.getInstance().getReference().child("todo_list").child("ToDo_List"+todoNum);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                            datasnapshot.getRef().child("titletodo").setValue(titletodo.getText().toString().trim());
                            datasnapshot.getRef().child("desctodo").setValue(desctodo.getText().toString().trim());
                            datasnapshot.getRef().child("datetodo").setValue(datetodo.getText().toString().trim());
                            datasnapshot.getRef().child("keytodo").setValue(keytodo);

                            Intent a = new Intent(NewTaskAct.this, MainActivity_Todo.class);
                            startActivity(a);
                            //Toast to display Save Successful Message
                            Toast.makeText(getApplicationContext(),"Added Successfullly", Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //Toast to display Save Unsuccessful Message
                            Toast.makeText(getApplicationContext(),"Cancelled!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private void cancelButton()   // To Clear Data in ======================>> Text filed
    {
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titletodo.setText("");
                datetodo.setText("");
                desctodo.setText("");
                //Toast to display Clear filed Successful Message
                Toast.makeText(getApplicationContext(),"Successfully Cleared", Toast.LENGTH_SHORT).show();
            }
        });

    }
}