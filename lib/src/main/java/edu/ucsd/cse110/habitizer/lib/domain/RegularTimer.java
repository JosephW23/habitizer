package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RegularTimer implements ElapsedTimer {
    private Timer timer;
    private TimerTask timerTask;
    private int secondsElapsed;
    private boolean isRunning;

    public RegularTimer() {
        this.secondsElapsed = -1;
        this.isRunning = false;
        this.timer = null;
        this.timerTask = null;
    }

    @Override
    public void startTimer() {
        if (isRunning) return;

        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        };

        // Safer scheduling method
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        }, 0, 1000);

        this.isRunning = true;
    }

    @Override
    public void stopTimer() {
        if (Objects.isNull(this.timerTask)) return;

        this.timerTask.cancel();
        this.timer.cancel();
        this.isRunning = false;
    }

    @Override
    public void pauseTimer() {
        if (!isRunning) return;

        this.timerTask.cancel();
        this.timer.cancel();
        this.isRunning = false;
    }

    @Override
    public void resumeTimer() {
        if (isRunning) return;

        this.startTimer();
    }

    @Override
    public void advanceTimer() {
        this.secondsElapsed += 30;
    }

    @Override
    public String getTime() {
        int hours = secondsElapsed / 3600;
        int minutes = (secondsElapsed % 3600) / 60;
        int seconds = secondsElapsed % 60;

        // Fixed locale issue
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
