package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoutineTest {

    @Test
    public void tasksGetter() {
        RegularTimer timer = new RegularTimer();
        // GIVEN a list of tasks
        var expected = List.of(
                new RoutineTask(0, "Brush Teeth", 1, false, timer),
                new RoutineTask(1, "Shower", 2, false, timer)
        );

        // WHEN I construct a Routine with those tasks
        Routine routine = new Routine("My Routine", expected);

        // THEN .tasks() returns the same tasks I created it with
        var actual = routine.tasks();
        assertEquals(expected, actual);
    }

    @Test
    public void blankTitleFailsValidation() {
        RegularTimer timer = new RegularTimer();
        // GIVEN ...
        // WHEN I try to create a Routine with a blank title.
        try {
            new Routine(" ", List.of(
                    new RoutineTask(0, "Brush Teeth", 1, false, timer)
            ));
            fail("Expected: IllegalArgumentException, blank title");
        }
        // THEN an IllegalArgumentException is thrown.
        catch (IllegalArgumentException e) {
            assertEquals("Routine title cannot be blank", e.getMessage());
        }

    }

    @Test
    public void titleIsCorrect() {
        RegularTimer timer = new RegularTimer();
        // GIVEN the title is "Test Routine"
        Routine routine = new Routine("Test Routine", List.of(
                new RoutineTask(1, "Brush Teeth", 1, false, timer)
        ));

        var expected = "Test Routine";
        // WHEN I get the routine title
        // THEN "Test Routine" is returned
        assertEquals(expected, routine.title());
    }

}