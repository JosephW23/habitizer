package edu.ucsd.cse110.habitizer.lib.domain;

public interface ElapsedTimer {

    // Starts a count-up timer
    public void startTimer();

    // Pauses a count-up timer
    public void stopTimer();

    // Gets the current time of the timer
    public String getTime();

}
