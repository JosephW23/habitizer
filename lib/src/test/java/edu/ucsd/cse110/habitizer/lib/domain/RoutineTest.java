package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoutineTest {

    @Test
    public void tasksGetter() {
        // GIVEN a list of tasks
        var expected = List.of(
                new RoutineTask(0, "Brush Teeth", 1, false, 0),
                new RoutineTask(1, "Shower", 2, false, 1)
        );

        // WHEN I construct a Routine with those tasks
        Routine routine = new Routine(0, "My Routine", 0);
        routine.setTasks(expected);

        // THEN .tasks() returns the same tasks I created it with
        var actual = routine.tasks();
        assertEquals(expected, actual);
    }

    @Test
    public void blankTitleFailsValidation() {
        // GIVEN ...
        // WHEN I try to create a Routine with a blank title.
        try {
            new Routine(0, " ", 0);
            fail("Expected: IllegalArgumentException, blank title");
        }
        // THEN an IllegalArgumentException is thrown.
        catch (IllegalArgumentException e) {
            assertEquals("Routine title cannot be blank", e.getMessage());
        }

    }

    @Test
    public void titleIsCorrect() {
        // GIVEN the title is "Test Routine"
        Routine routine = new Routine(0, "Test Routine", 0);
        routine.setTasks(List.of(
                new RoutineTask(1, "Brush Teeth", 1, false, 0)
        ));

        var expected = "Test Routine";
        // WHEN I get the routine title
        // THEN "Test Routine" is returned
        assertEquals(expected, routine.title());
    }

    @Test
    public void checkId() {
        // GIVEN the title is "Test Routine"
        Routine routine = new Routine(0, "Test Routine", 0);
        routine.setTasks(List.of(
                new RoutineTask(1, "Brush Teeth", 1, false, 0)
        ));

        var expected = 0;
        // WHEN I get the routine title
        // THEN "Test Routine" is returned
        assertEquals(expected, routine.id());
    }

    @Test
    public void checkSortOrder() {
        int expectedSortOrder;

        Routine routine;

        routine = new Routine(0, "Test Routine", 0);
        expectedSortOrder = 0;
        assertEquals(expectedSortOrder, routine.sortOrder());

        routine = new Routine(0, "Test Routine", 3);
        expectedSortOrder = 3;
        assertEquals(expectedSortOrder, routine.sortOrder());

    }

}