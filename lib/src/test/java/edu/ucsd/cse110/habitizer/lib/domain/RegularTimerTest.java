package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RegularTimerTest {
    @Test
    public void getTime() {
        // GIVEN I have a timer
        // AND its current time is zero
        var timer = new RegularTimer();
        // WHEN I get the time
        var actual = timer.getTime();
        // THEN the timer should have a time of "0"
        var expected = "00:00";
        assertEquals(expected, actual);
    }

    @Test
    public void startTimer() throws InterruptedException {
        // GIVEN I have a timer
        var timer = new RegularTimer();
        // WHEN I start the timer
        timer.startTimer();
        Thread.sleep(1100);
        // THEN the timer should have a time of "00:01"
        var expected = "00:01";
        var actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void stopTimer() throws InterruptedException {
        // GIVEN I have a timer that is running
        var timer = new RegularTimer();
        timer.startTimer();
        Thread.sleep(1100);
        // WHEN I stop the timer
        timer.stopTimer();
        // THEN the timer should be reset to "00:00"
        var expected = "00:00";
        var actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void pauseTimer() throws InterruptedException {
        // GIVEN I have a timer that is running
        var timer = new RegularTimer();
        timer.startTimer();
        Thread.sleep(1100);
        // WHEN I pause the timer
        timer.pauseTimer();
        var timeAfterPause = timer.getTime();
        // Wait another second
        Thread.sleep(1100);
        // THEN the time should not have advanced further
        assertEquals(timeAfterPause, timer.getTime());
    }

    @Test
    public void resumeTimer() throws InterruptedException {
        // GIVEN I have a timer that is running and then paused
        var timer = new RegularTimer();
        timer.startTimer();
        Thread.sleep(1100); // Timer should be at "00:01"
        timer.pauseTimer();
        var timeAtPause = timer.getTime(); // e.g., "00:01"
        // WHEN I resume the timer
        timer.resumeTimer();
        Thread.sleep(1100);
        // Should tick one more second
        // THEN the timer should have advanced by one second after resume
        // e.g., "00:01" -> "00:02"
        timer.pauseTimer();
        var expected = "00:02";
        var actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void advanceTimer() throws InterruptedException {
        // GIVEN I have a timer that is running
        var timer = new RegularTimer();
        timer.startTimer();
        Thread.sleep(1100); // Timer should be at "00:01"
        // WHEN I manually advance the timer by 30 seconds
        timer.advanceTimer();
        // THEN the elapsed time should reflect a 30 second increase (1 + 30 = 31 seconds)
        var expected = "00:31";
        var actual = timer.getTime();
        assertEquals(expected, actual);
    }
}
