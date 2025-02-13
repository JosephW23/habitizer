package edu.ucsd.cse110.habitizer.lib.domain;

public interface ElapsedTimer {

    // Starts a count-up timer
    public void startTimer();

    // Pauses a count-up timer
    public void stopTimer();

    // Gets the current time of the timer
    public String getTime();

    // Allows pausing the timer
    void pauseTimer();

    // Allows resuming the timer after pausing
    void resumeTimer();

    // Advances the timer by 30 seconds (For testing in US3c)
    void advanceTimer();

}
