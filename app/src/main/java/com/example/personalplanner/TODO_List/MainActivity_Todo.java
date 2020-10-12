package com.example.personalplanner.TODO_List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity_Todo extends AppCompatActivity  {

    //Initialize Variable
    TextView titlepage;
    TextView subtitlepage;
    TextView endpage;
    Button btnAddNew;


    DatabaseReference reference;
    RecyclerView ourtodo;
    ArrayList<MyTodo> list;
    TodoAdapter todoAdapter;
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity_main);

        viewData();
        addNewButton();

    }



    @Override
    protected void onStart()
    {
        super.onStart();
        searchTodo();
    }


    private void addNewButton()
    {
        //set the (+) Plus button and navigate to new page
        btnAddNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent b = new Intent(MainActivity_Todo.this, NewTaskAct.class);
                startActivity(b);
            }
        });
    }

    private void viewData()
    {
        //Assign variables
        titlepage = (TextView) findViewById(R.id.titlepage);
        subtitlepage = (TextView) findViewById(R.id.subtitlepage);
        endpage = (TextView) findViewById(R.id.endpage);
        btnAddNew = (Button) findViewById(R.id.btnAddNew);

        //working with data
        ourtodo = findViewById(R.id.ourtodo);
        ourtodo.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTodo>();


        //view Date from <<============================== (FireBase)
        reference = FirebaseDatabase.getInstance().getReference().child("todo_list") ;
        reference.addValueEventListener(new ValueEventListener()
        {
            //reade every Node in database and display
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //set code to review the data. from database
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    MyTodo p = dataSnapshot1.getValue(MyTodo.class);
                    list.add(p);
                }
                todoAdapter = new TodoAdapter(MainActivity_Todo.this, list);
                ourtodo.setAdapter(todoAdapter);
                todoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //set cod to show an error // set the toast to display error
                Toast.makeText(getApplicationContext(), "View Data Error", Toast.LENGTH_LONG).show();
            }
        });

    }


    private void searchTodo()
    {
        searchView = (SearchView)findViewById(R.id.searchlist);

        if (reference != null)
        {
            reference.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if (snapshot.exists())
                    {

                        list = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren())
                        {
                            list.add(ds.getValue(MyTodo.class));
                        }
                        TodoAdapter todoAdapter = new TodoAdapter(MainActivity_Todo.this,list);
                        ourtodo.setAdapter(todoAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(getApplicationContext(), "View Data Error", Toast.LENGTH_LONG).show();
                }
            });
        }

        if (searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s)
                {
                    search(s);
                    return true;
                }
            });
        }
    }

    // onQueryTextChange(Search)
    private void search(String str)
    {
        ArrayList<MyTodo> myList = new ArrayList<>();
        for (MyTodo object : list)
        {
            if (object.getDatetodo().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }

        TodoAdapter todoAdapter = new TodoAdapter(MainActivity_Todo.this,myList);
        ourtodo.setAdapter(todoAdapter);
        todoAdapter.notifyDataSetChanged();
    }
}





