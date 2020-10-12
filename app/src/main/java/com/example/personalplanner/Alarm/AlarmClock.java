package com.example.personalplanner.Alarm;

import java.io.Serializable;

public class AlarmClock implements Serializable {
    private String id;
    private String time;
    private boolean isRepeat;
    private boolean isSnooze;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public boolean isSnooze() {
        return isSnooze;
    }

    public void setSnooze(boolean snooze) {
        isSnooze = snooze;
    }
}
