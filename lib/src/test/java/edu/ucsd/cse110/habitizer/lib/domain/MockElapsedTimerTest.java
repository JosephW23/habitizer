package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class MockElapsedTimerTest {
    @Test
    public void advanceTimer() {
        // GIVEN I have a timer
        // AND its current time is zero
        var timer = new MockElapsedTimer();
        // WHEN I advance the time
        timer.advanceTimer();
        // THEN the timer should have a time of 30
        var actual = timer.getTime();
        var expected = "30";
        assertEquals(expected, actual);
    }
}
