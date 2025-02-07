package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RoutineTest {

    @Test
    public void tasks() {
        Routine routine = new Routine("Test Routine", List.of(
                new RoutineTask("Brush Teeth", 1, false),
                new RoutineTask("Shower", 2, false)
        ));

        List<RoutineTask> expected = new ArrayList<RoutineTask>(List.of(
                new RoutineTask("Brush Teeth", 1, false),
                new RoutineTask("Shower", 2, false)
        ));

        assertEquals(routine.tasks(), expected);
    }

    @Test
    public void title() {
        Routine routine = new Routine("Test Routine", List.of(
                new RoutineTask("Brush Teeth", 1, false)
        ));

        var expected = "Test Routine";
        assertEquals(routine.title(), expected);

        // Try-Catch for blank title
        try {
            new Routine(" ", List.of(
                    new RoutineTask("Brush Teeth", 1, false)
                ));
            fail("Expected: IllegalArgumentException, blank title");
        } catch (IllegalArgumentException e) {
            assertEquals("Routine title cannot be blank", e.getMessage());
        }

        // Try-Catch for null title
        try {
            new Routine(null, List.of(
                    new RoutineTask("Brush Teeth", 1, false)
            ));
            fail("Expected: IllegalArgumentException, null title");
        } catch (IllegalArgumentException e) {
            assertEquals("Routine title cannot be blank", e.getMessage());
        }
    }
}