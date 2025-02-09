package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoutineTaskTest {

    @Test
    public void title() {
        var task = new RoutineTask(0, "Brush Teeth", 1, false);
        assertEquals("Brush Teeth", task.title());

        // Try-Catch for blank title
        try {
            new RoutineTask(0, " ", 1, false);
            fail("Expected: IllegalArgumentException, blank title");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask title cannot be blank", e.getMessage());
        }

        // Try-Catch for null title
        try {
            new RoutineTask(0, null, 1, false);
            fail("Expected: IllegalArgumentException, null title");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask title cannot be blank", e.getMessage());
        }
    }

    @Test
    public void priority() {
        var task = new RoutineTask(0, "Brush Teeth", 1, false);
        assertEquals(1, task.priority());

        // Try-Catch for negative priority
        try {
            new RoutineTask(0, "Brush Teeth", -1, false);
            fail("Expected: IllegalArgumentException, negative priority");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask priority must be an integer greater than 0", e.getMessage());
        }

        // Try-Catch for zero priority
        try {
            new RoutineTask(0,"Brush Teeth", 0, false);
            fail("Expected: IllegalArgumentException, zero priority");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask priority must be an integer greater than 0", e.getMessage());
        }
    }

    @Test
    public void isChecked() {
        // Check: Initializing isChecked == False
        var falseIsCheckedTask = new RoutineTask(0, "Brush Teeth", 1, false);
        assertFalse(falseIsCheckedTask.isChecked());

        // Check: Initializing isChecked == True
        var trueIsCheckedTask = new RoutineTask(0, "Brush Teeth", 1, true);
        assertTrue(trueIsCheckedTask.isChecked());
    }

    @Test
    public void id() {
        Integer expectedId;

        var taskWithZeroId = new RoutineTask(0, "Brush Teeth", 1, false);
        expectedId = 0;
        assertEquals(expectedId, taskWithZeroId.id());

        var taskWithNonZeroId = new RoutineTask(3, "Brush Teeth", 1, false);
        expectedId = 3;
        assertEquals(expectedId, taskWithNonZeroId.id());

        var taskWithNegativeId = new RoutineTask(-2, "Brush Teeth", 1, false);
        expectedId = -2;
        assertEquals(expectedId, taskWithNegativeId.id());

        var taskWithNullId = new RoutineTask(null, "Brush Teeth", 1, false);
        expectedId = null;
        assertEquals(expectedId, taskWithNullId.id());
    }

    @Test
    public void checkOff() {
        Integer expectedId;

        var task = new RoutineTask(0, "Brush Teeth", 1, false);

        assertFalse(task.isChecked());
        task.checkOff();
        assertTrue(task.isChecked());
    }
}