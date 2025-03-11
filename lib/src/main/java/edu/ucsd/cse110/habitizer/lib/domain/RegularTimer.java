package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RegularTimer implements ElapsedTimer {
    private Timer timer;
    private TimerTask timerTask;
    private int secondsElapsed;
    private int secondsFinal;
    private boolean isRunning;
    public static final long TIMER_INTERVAL_MS = 1000;

    public RegularTimer() {
        this.secondsElapsed = 0;
        this.secondsFinal = 0;
        this.isRunning = false;
        this.timer = null;
        this.timerTask = null;
    }

    @Override
    public void startTimer() {
        if (isRunning) return;

        this.timer = new Timer();

        timer.schedule(timerTask = new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        }, TIMER_INTERVAL_MS, TIMER_INTERVAL_MS);

        isRunning = true;
    }

    @Override
    public void stopTimer() {
        if (!isRunning && timer == null) return;

        if (timerTask != null) timerTask.cancel();
        if (timer != null) timer.cancel();

        isRunning = false;
        secondsFinal = secondsElapsed;
    }

    @Override
    public void pauseTimer() {
        if (!isRunning) return;

        if (timerTask != null) timerTask.cancel();
        if (timer != null) timer.cancel();

        isRunning = false;
        secondsFinal = secondsElapsed;
    }

    @Override
    public void resumeTimer() {
        if (isRunning) return;

        this.timer = new Timer();
        timer.schedule(this.timerTask = new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
            }
        }, TIMER_INTERVAL_MS, TIMER_INTERVAL_MS);

        this.isRunning = true;
    }

    @Override
    public void advanceTimer() {
        this.secondsElapsed += 30;
    }

    @Override
    public void resetTimer() {
        stopTimer();
        this.secondsElapsed = 0;
        this.isRunning = false;
        this.timer = null;
        this.timerTask = null;
        startTimer();
    }

    @Override
    public String getTime() {
        int minutes;
        int seconds;

        if (!isRunning) {
            minutes = (secondsFinal % 3600) / 60;
            seconds = secondsFinal % 60;
        } else {
            minutes = (secondsElapsed % 3600) / 60;
            seconds = secondsElapsed % 60;
        }

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    @Override
    public int getSeconds() {
        return secondsElapsed;
    }

    @Override
    public void setSeconds(int seconds) {
        this.secondsElapsed = seconds;
    }

    @Override
    public String getRoundedUpTime() {
        int minutes = isRunning ? (secondsElapsed % 3600) / 60 : (secondsFinal % 3600) / 60;
        return String.format(Locale.getDefault(), "%01d", minutes + 1);
    }

    @Override
    public String getRoundedDownTime() {
        int minutes = isRunning ? (secondsElapsed % 3600) / 60 : (secondsFinal % 3600) / 60;
        return minutes == 0 ? "-" : String.format(Locale.getDefault(), "%01d", minutes);
    }

    /** Minimal change: Added this method to match MockElapsedTimer **/
    public boolean isRunning() {
        return isRunning;
    }
}