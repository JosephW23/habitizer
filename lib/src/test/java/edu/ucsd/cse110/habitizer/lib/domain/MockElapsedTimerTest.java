package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MockElapsedTimerTest {
    private MockElapsedTimer timer;

    @Before
    public void setUp() {
        timer = new MockElapsedTimer();
    }

    @Test
    public void immediateTimer() {
        // GIVEN a newly UI-test timer that has just been instantiated (testing static method)
        MockElapsedTimer immediateTimer = MockElapsedTimer.immediateTimer();
        // WHEN I get the time immediately
        String actual = immediateTimer.getTime();
        // THEN the timer should have a time of "00:00"
        String expected = "00:00";
        immediateTimer.stopTimer(); // (Stopping timer just in case)
        assertEquals(expected, actual);
    }

    @Test
    public void startTimer() throws InterruptedException {
        // GIVEN a timer
        // WHEN I start the timer and wait for approximately one second
        timer.startTimer();
        Thread.sleep(1100);
        // THEN the elapsed time should be about 1 second (i.e. "00:01")
        String expected = "00:01";
        String actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void stopTimer() throws InterruptedException {
        // GIVEN a timer that is running
        timer.startTimer();
        Thread.sleep(1100);
        // WHEN I stop the timer
        timer.stopTimer();
        // THEN the timer should be reset, reporting "00:00"
        String expected = "00:00";
        String actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void pauseTimer() throws InterruptedException {
        // GIVEN a timer that is running
        timer.startTimer();
        Thread.sleep(1100);
        // WHEN I pause the timer
        timer.pauseTimer();
        String pausedTime = timer.getTime();
        // Wait another second
        Thread.sleep(1100);
        // THEN the elapsed time should remain unchanged while paused
        String actual = timer.getTime();
        assertEquals(pausedTime, actual);
    }

    @Test
    public void resumeTimer() throws InterruptedException {
        // GIVEN a timer that is running and then paused
        timer.startTimer();
        Thread.sleep(1100); // Should be about "00:01"
        timer.pauseTimer();
        String timeAtPause = timer.getTime(); // e.g., "00:01"
        // WHEN I resume the timer and wait for approximately one second
        timer.resumeTimer();
        Thread.sleep(1100);
        // THEN the elapsed time should continue from where it left off
        // If timeAtPause was "00:01", then after one more second we expect "00:02"
        String expected = "00:02";
        String actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void advanceTimer() throws InterruptedException {
        // GIVEN a timer that is running
        timer.startTimer();
        Thread.sleep(1100); // Timer should be roughly at "00:01"
        // WHEN I advance the timer by 30 seconds
        timer.advanceTimer();
        // THEN the elapsed time should reflect an increase of 30 seconds
        // (e.g., "00:01" becomes "00:31")
        String expected = "00:31";
        String actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void getTime() throws InterruptedException {
        // GIVEN a timer that is started
        timer.startTimer();
        // WHEN I wait for about 3 seconds
        Thread.sleep(3100);
        // THEN getTime() should return "00:03"
        String expected = "00:03";
        String actual = timer.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void isRunning() throws InterruptedException {
        // GIVEN a timer that has not been started
        assertFalse(timer.isRunning());

        // WHEN I start the timer
        timer.startTimer();
        // THEN the timer should return that it is running
        assertTrue(timer.isRunning());

        // WHEN I pause the timer
        timer.pauseTimer();
        // THEN the timer should return that it is NOT running
        assertFalse(timer.isRunning());

        // WHEN I resume the timer
        timer.resumeTimer();
        // THEN the timer should return that it is running
        assertTrue(timer.isRunning());

        // WHEN I stop the timer
        timer.stopTimer();
        // THEN the timer should return that it is NOT running
        assertFalse(timer.isRunning());
    }
}

