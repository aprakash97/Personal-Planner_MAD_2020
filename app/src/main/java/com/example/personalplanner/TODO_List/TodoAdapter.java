package com.example.personalplanner.TODO_List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalplanner.R;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder>  {

    //Initialize Variable
    Context context;
    ArrayList<MyTodo> myTodo;

    public TodoAdapter(Context c, ArrayList<MyTodo> p){
        context = c;
        myTodo = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.titletodo.setText(myTodo.get(i).getTitletodo());
        myViewHolder.desctodo.setText(myTodo.get(i).getDesctodo());
        myViewHolder.datetodo.setText(myTodo.get(i).getDatetodo());

        //Once the press on the Event it directed to the Edit Details activity and using putExtra Fill the text areas.
        final String getTitleTodo = myTodo.get(i).getTitletodo();
        final String getDescTodo = myTodo.get(i).getDesctodo();
        final String getDateTodo = myTodo.get(i).getDatetodo();
        final String getKeytodo = myTodo.get(i).getKeytodo();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aa = new Intent(context,UpdateAndDelete.class);
                aa.putExtra("titletodo", getTitleTodo);
                aa.putExtra("desctodo", getDescTodo);
                aa.putExtra("datetodo", getDateTodo);
                aa.putExtra("keytodo", getKeytodo);
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTodo.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titletodo;
        TextView desctodo;
        TextView datetodo;
        TextView keytodo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletodo =(TextView)itemView.findViewById(R.id.titletodo);
            desctodo =(TextView)itemView.findViewById(R.id.desctodo);
            datetodo =(TextView)itemView.findViewById(R.id.datetodo);

        }
    }
}
