package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoutineTaskTest {

    @Test
    public void title() {
        var task = new RoutineTask(0, "Brush Teeth", 1, false, 0);
        assertEquals("Brush Teeth", task.title());

        // Try-Catch for blank title
        try {
            new RoutineTask(0, " ", 1, false, 0);
            fail("Expected: IllegalArgumentException, blank title");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask title cannot be blank", e.getMessage());
        }

    }

    @Test
    public void priority() {
        var task = new RoutineTask(0, "Brush Teeth", 1, false, 0);
        assertEquals(1, task.priority());

        // Try-Catch for negative priority
        try {
            new RoutineTask(0, "Brush Teeth", -1, false, 0);
            fail("Expected: IllegalArgumentException, negative priority");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask priority must be an integer greater than 0", e.getMessage());
        }

        // Try-Catch for zero priority
        try {
            new RoutineTask(0,"Brush Teeth", 0, false, 0);
            fail("Expected: IllegalArgumentException, zero priority");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask priority must be an integer greater than 0", e.getMessage());
        }
    }

    @Test
    public void isChecked() {
        // Check: Initializing isChecked == False
        var falseIsCheckedTask = new RoutineTask(0, "Brush Teeth", 1, false, 0);
        assertFalse(falseIsCheckedTask.isChecked());

        // Check: Initializing isChecked == True
        var trueIsCheckedTask = new RoutineTask(0, "Brush Teeth", 1, true, 0);
        assertTrue(trueIsCheckedTask.isChecked());
    }

    @Test
    public void id() {
        Integer expectedId;

        var taskWithZeroId = new RoutineTask(0, "Brush Teeth", 1, false, 0);
        expectedId = 0;
        assertEquals(expectedId, taskWithZeroId.id());

        var taskWithNonZeroId = new RoutineTask(3, "Brush Teeth", 1, false, 0);
        expectedId = 3;
        assertEquals(expectedId, taskWithNonZeroId.id());

        var taskWithNegativeId = new RoutineTask(-2, "Brush Teeth", 1, false, 0);
        expectedId = -2;
        assertEquals(expectedId, taskWithNegativeId.id());

        var taskWithNullId = new RoutineTask(null, "Brush Teeth", 1, false, 0);
        expectedId = null;
        assertEquals(expectedId, taskWithNullId.id());
    }

    @Test
    public void checkOff() {
        Integer expectedId;

        RegularTimer timer = new RegularTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, 0);

        assertFalse(task.isChecked());
        task.checkOff(timer.getTime());
        assertTrue(task.isChecked());
    }

    @Test
    public void checkOffTwice() {
        // GIVEN I have a task Brush Teeth
        Integer expectedId;

        RegularTimer timer = new RegularTimer();

        var task = new RoutineTask(0, "Brush Teeth", 1, false, 0);
        // WHEN I check off Brush Teeth
        task.checkOff(timer.getTime());
        // THEN Brush Teeth isChecked
        assertTrue(task.isChecked());
        // WHEN I check off Brush Teeth again
        task.checkOff(timer.getTime());
        // THEN Brush Teeth is(still)Checked
        assertTrue(task.isChecked());
    }
}