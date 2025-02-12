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
    public void advanceTimer() {
        var timer = new MockElapsedTimer();
        timer.startTimer();
        timer.advanceTimer();
        assertEquals("00:00:30", timer.getTime());
    }

    @Test
    public void pauseKeepsTime() {
        timer.startTimer();
        timer.advanceTimer();
        String beforePause = timer.getTime();

        timer.pauseTimer();
        timer.advanceTimer(); // Should NOT change time

        assertEquals(beforePause, timer.getTime());
    }

    @Test
    public void resumeContinuesTime() {
        timer.startTimer();
        timer.advanceTimer();
        timer.pauseTimer();

        timer.resumeTimer();
        timer.advanceTimer();

        assertEquals("00:01:00", timer.getTime());
    }

    @Test
    public void stopResetsTime() {
        timer.startTimer();
        timer.advanceTimer();
        timer.advanceTimer();

        timer.stopTimer();

        assertEquals("00:00:00", timer.getTime());
    }
    // Test that elapsed time rounds correctly
    @Test
    public void elapsedTimeRounding() {
        timer.startTimer();
        timer.advanceTimer(); // 30s
        timer.advanceTimer(); // 60s
        timer.advanceTimer(); // 90s (1m 30s)

        assertEquals("00:01:30", timer.getTime()); // Ensures correct rounding
    }

    //Test that pausing stops time progression
    @Test
    public void pausingStopsTimeProgression() {
        timer.startTimer();
        timer.advanceTimer(); // 30s
        timer.pauseTimer();
        timer.advanceTimer(); // Should NOT increment

        assertEquals("00:00:30", timer.getTime()); // Should remain at 30s
    }

    // Test that resuming continues time updates correctly
    @Test
    public void testResumingAfterPause() {
        timer.startTimer();
        timer.advanceTimer(); // 30s
        timer.pauseTimer();
        timer.resumeTimer();
        timer.advanceTimer(); // 60s

        assertEquals("00:01:00", timer.getTime()); // Should continue from last state
    }
    // New Tests for Manually Advancing Time (US3c)

    @Test
    public void testManualAdvanceOnce() {
        timer.startTimer();
        timer.advanceTimer();
        assertEquals("00:00:30", timer.getTime()); // Should be 30s
    }

    @Test
    public void testManualAdvanceMultipleTimes() {
        timer.startTimer();
        timer.advanceTimer();
        timer.advanceTimer();
        timer.advanceTimer();
        assertEquals("00:01:30", timer.getTime()); // Should be 1m 30s
    }

    /* @Test
   // public void testManualAdvanceWhilePaused() {
        timer.startTimer();
        timer.advanceTimer(); // 30s
        timer.pauseTimer();

        timer.advanceTimer(); // Should still advance even when paused
        assertEquals("00:01:00", timer.getTime()); // Should be 60s (1m)
    }*/

}

