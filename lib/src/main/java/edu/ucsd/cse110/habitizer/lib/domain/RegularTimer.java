package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RegularTimer implements ElapsedTimer {
    private Timer timer;
    private TimerTask timerTask;
    private int secondsElapsed; // Needed to keep track of seconds
    private boolean isRunning;  // Needed to track status of timer

    public RegularTimer() {
        this.secondsElapsed = 0; // Needs to be -1 since the timer begins IMMEDIATELY (i.e. resolves to 0 when started)
        this.isRunning = false;
        this.timer = null;     // Not instantiated until we startTimer()
        this.timerTask = null; // Also not instantiated until we startTimer()
    }

    @Override
    public void startTimer() {
        // Should handle starting the timer again even if the timer has already been started
        if (isRunning == true) return;
        
        // If the timer was stopped, ensure a fresh start by leaving secondsElapsed as is (i.e. -1) so that
        // the immediate tick sets it to 0.
        this.timer = new Timer();

        // Safer scheduling method compared to previous commit
        timer.schedule(timerTask = new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        }, 1000, 1000);

        isRunning = true;
    }

    @Override
    public void stopTimer() {
        // Should handle stopping the timer even if the timer hasn't been started
        if (isRunning == false && timer == null) return;

        if (!Objects.isNull(timerTask)) timerTask.cancel();

        if (!Objects.isNull(timer)) timer.cancel();

        isRunning = false;
        secondsElapsed = 0;
    }

    @Override
    public void pauseTimer() {
        if (isRunning == false) return;

        if (!Objects.isNull(timerTask)) timerTask.cancel();

        if (!Objects.isNull(timer)) timer.cancel();

        isRunning = false;
    }

    @Override
    public void resumeTimer() {
        // Only resume if the timer was paused.
        if (isRunning == true || secondsElapsed < 0) return;

        // Resume the timer by creating a new Timer and TimerTask without resetting secondsElapsed.
        this.timer = new Timer();
        timer.schedule(this.timerTask = new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        }, 1000, 1000);

        this.isRunning = true;
    }

    @Override
    public void advanceTimer() {
        // Advance timer by 30 seconds regardless of whether it's paused
        this.secondsElapsed += 30;
    }

    @Override
    public String getTime() {
        // If the timer hasn't started or has been stopped, return "00:00"
        if (secondsElapsed < 0) {
            return "00:00";
        }

        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;

        // Using Locale to test whether or not this is better than just a formatted string
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}