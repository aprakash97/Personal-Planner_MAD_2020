package com.example.personalplanner.TODO_List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAndDelete extends AppCompatActivity {

    //Initialize Variable
    EditText titleTodo;
    EditText descTodo;
    EditText dateTodo;
    Button updatebtnn;
    Button btnDelete;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_update_and_delete);

        buttonUpdate();
        buttonDelete();

    }

    private void buttonUpdate()   // For Update Data To ======================>> Firebase
    {

        titleTodo = (EditText) findViewById(R.id.titletodo);
        descTodo = (EditText) findViewById(R.id.desctodo);
        dateTodo = (EditText) findViewById(R.id.datetodo);
        updatebtnn = (Button)findViewById(R.id.updatebtn);

        //get values from previous page  <<======= (Main page)
        titleTodo.setText(getIntent().getStringExtra("titletodo"));
        descTodo.setText(getIntent().getStringExtra("desctodo"));
        dateTodo.setText(getIntent().getStringExtra("datetodo"));
        final String keykeytodo = getIntent().getStringExtra("keytodo");

        updatebtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (titleTodo.length() == 0){
                    titleTodo.setError("Title Filed Can't be Empty");
                }else if (descTodo.length() == 0){
                    descTodo.setError("Description Can't be Empty");
                }else if (dateTodo.length() == 0){
                    dateTodo.setError("Timeline Can't be Empty");
                }else{
                    reference = FirebaseDatabase.getInstance().getReference().child("todo_list").child("ToDo_List"+ keykeytodo);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            snapshot.getRef().child("titletodo").setValue(titleTodo.getText().toString());
                            snapshot.getRef().child("desctodo").setValue(descTodo.getText().toString());
                            snapshot.getRef().child("datetodo").setValue(dateTodo.getText().toString());
                            snapshot.getRef().child("keytodo").setValue(keykeytodo);

                            Intent aa = new Intent(UpdateAndDelete.this, MainActivity_Todo.class);
                            startActivity(aa);
                            //Toast to display Update Successful Message
                            Toast.makeText(getApplicationContext(),"Update Successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //Toast to display Update Unsuccessful Message
                            Toast.makeText(getApplicationContext(),"Update Unsuccessfully!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void buttonDelete()    // To Delete Data from ======================>> Firebase
    {
        btnDelete = (Button)findViewById(R.id.btnDelete);

        final String keykeytodo = getIntent().getStringExtra("keytodo");

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference = FirebaseDatabase.getInstance().getReference().child("todo_list").child("ToDo_List" + keykeytodo);
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Intent a = new Intent( UpdateAndDelete.this, MainActivity_Todo.class);
                            startActivity(a);
                            //Toast to display delete Successful Message
                            Toast.makeText(getApplicationContext(),"Delete Successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            //Toast to display Delete Unsuccessful Message
                            Toast.makeText(getApplicationContext(),"Delete Unsuccessfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}