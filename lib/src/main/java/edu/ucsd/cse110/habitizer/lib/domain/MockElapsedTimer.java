package edu.ucsd.cse110.habitizer.lib.domain;

import java.time.temporal.Temporal;

public class MockElapsedTimer implements ElapsedTimer {
    private Temporal timer;
    MockElapsedTimer() {

    }

    public void startTimer() {
        return;
    }

    public void stopTimer() {

    }

    @Override
    public String getTime() {
        return "";
    }

    // Advances the timer by 30 seconds
    public void advanceTimer() {

    }
}
