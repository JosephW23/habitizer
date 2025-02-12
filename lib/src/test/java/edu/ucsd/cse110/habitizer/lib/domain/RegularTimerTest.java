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
        var expected = "0";
        assertEquals(expected, actual);
    }
}
