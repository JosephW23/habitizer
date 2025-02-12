package edu.ucsd.cse110.habitizer.lib.domain;

import java.time.temporal.Temporal;
import java.util.Timer;
import java.util.TimerTask;

public class RegularTimer implements ElapsedTimer {
    private Timer timer;
    private TimerTask timerTask;

    RegularTimer() {
        this.timer = new Timer();
    }

    public void startTimer() {
        return;
    }

    public void stopTimer() {
        return;
    }

    @Override
    public String getTime() {
        return "";
    }
}
