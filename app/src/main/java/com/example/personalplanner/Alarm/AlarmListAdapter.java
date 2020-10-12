package com.example.personalplanner.Alarm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.personalplanner.R;
import com.example.personalplanner.Alarm.AlarmClock;
import com.example.personalplanner.R;

import java.util.ArrayList;
import java.util.List;


public class AlarmListAdapter extends ArrayAdapter<AlarmClock> {
    Activity context;
    List<AlarmClock> items;

    public AlarmListAdapter(@NonNull Activity context, ArrayList<AlarmClock> dataArrayList) {
        super(context, 0, dataArrayList);
        this.context=context;
        this.items=dataArrayList;
    }

    private class ViewHolder {

        TextView Repeat;
        TextView time;
        TextView Snooze;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.alarm_time_lists, parent, false);

            holder = new AlarmListAdapter.ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.timer_time);

            holder.Repeat = (TextView) convertView.findViewById(R.id.repeat_alarm);
            holder.Snooze=(TextView)convertView.findViewById(R.id.snooze_alarm);
            convertView.setTag(holder);

        } else {
            holder = (AlarmListAdapter.ViewHolder) convertView.getTag();
        }

        AlarmClock stopWatch = items.get(position);

        holder.Repeat.setText(stopWatch.isRepeat()?"repeat alarm":"No-repeat alarm");
        holder.time.setText(stopWatch.getTime());
    holder.Snooze.setText(stopWatch.isSnooze()?"Snooze On":"Snooze Off");
        return convertView;

    }


}
