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
    }
}