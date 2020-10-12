package com.example.personalplanner.Timer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.personalplanner.R;
import com.example.personalplanner.Timer.StopWatch;

import java.util.ArrayList;
import java.util.List;


public class TimeListAdapter extends ArrayAdapter<StopWatch> {
    Activity context;
    List<StopWatch> items;

    public TimeListAdapter(@NonNull Activity context, ArrayList<StopWatch> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }

    private class ViewHolder {

        TextView name;
        TextView time;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimeListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.time_lists, parent, false);

            holder = new TimeListAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.timer_name);

            holder.time = (TextView) convertView.findViewById(R.id.timer_time);

            convertView.setTag(holder);

        } else {
            holder = (TimeListAdapter.ViewHolder) convertView.getTag();
        }

        StopWatch stopWatch = items.get(position);

        holder.name.setText(stopWatch.getName());
        holder.time.setText(stopWatch.getTime());

        return convertView;

    }


}
