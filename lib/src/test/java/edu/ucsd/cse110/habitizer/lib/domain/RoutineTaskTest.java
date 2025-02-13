package edu.ucsd.cse110.habitizer.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoutineTaskTest {

    @Test
    public void title() {
        RegularTimer timer = new RegularTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);
        assertEquals("Brush Teeth", task.title());

        // Try-Catch for blank title
        try {
            new RoutineTask(0, " ", 1, false, timer);
            fail("Expected: IllegalArgumentException, blank title");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask title cannot be blank", e.getMessage());
        }

    }

    @Test
    public void priority() {
        RegularTimer timer = new RegularTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);
        assertEquals(1, task.priority());

        // Try-Catch for negative priority
        try {
            new RoutineTask(0, "Brush Teeth", -1, false, timer);
            fail("Expected: IllegalArgumentException, negative priority");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask priority must be an integer greater than 0", e.getMessage());
        }

        // Try-Catch for zero priority
        try {
            new RoutineTask(0,"Brush Teeth", 0, false, timer);
            fail("Expected: IllegalArgumentException, zero priority");
        } catch (IllegalArgumentException e) {
            assertEquals("RoutineTask priority must be an integer greater than 0", e.getMessage());
        }
    }

    @Test
    public void isChecked() {
        RegularTimer timer = new RegularTimer();
        // Check: Initializing isChecked == False
        var falseIsCheckedTask = new RoutineTask(0, "Brush Teeth", 1, false, timer);
        assertFalse(falseIsCheckedTask.isChecked());

        // Check: Initializing isChecked == True
        var trueIsCheckedTask = new RoutineTask(0, "Brush Teeth", 1, true, timer);
        assertTrue(trueIsCheckedTask.isChecked());
    }

    @Test
    public void id() {
        Integer expectedId;
        RegularTimer timer = new RegularTimer();

        var taskWithZeroId = new RoutineTask(0, "Brush Teeth", 1, false, timer);
        expectedId = 0;
        assertEquals(expectedId, taskWithZeroId.id());

        var taskWithNonZeroId = new RoutineTask(3, "Brush Teeth", 1, false, timer);
        expectedId = 3;
        assertEquals(expectedId, taskWithNonZeroId.id());

        var taskWithNegativeId = new RoutineTask(-2, "Brush Teeth", 1, false, timer);
        expectedId = -2;
        assertEquals(expectedId, taskWithNegativeId.id());

        var taskWithNullId = new RoutineTask(null, "Brush Teeth", 1, false, timer);
        expectedId = null;
        assertEquals(expectedId, taskWithNullId.id());
    }

    @Test
    public void checkOff() {
        Integer expectedId;

        RegularTimer timer = new RegularTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);

        assertFalse(task.isChecked());
        task.checkOff();
        assertTrue(task.isChecked());
    }

    @Test
    public void checkOffTwice() {
        // GIVEN I have a task Brush Teeth
        Integer expectedId;

        RegularTimer timer = new RegularTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);
        // WHEN I check off Brush Teeth
        task.checkOff();
        // THEN Brush Teeth isChecked
        assertTrue(task.isChecked());
        // WHEN I check off Brush Teeth again
        task.checkOff();
        // THEN Brush Teeth is(still)Checked
        assertTrue(task.isChecked());
    }

    @Test
    public void startTask() throws InterruptedException{
        MockElapsedTimer timer = new MockElapsedTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);

        task.start();
        Thread.sleep(1100);
        String expected = "00:01";
        String actual = task.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void endTask() throws InterruptedException {
        MockElapsedTimer timer = new MockElapsedTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);

        task.start();
        Thread.sleep(1100);
        task.end();
        String expected = "00:01";
        String actual = task.getTime();
        assertEquals(expected, actual);
    }

    @Test
    public void getTime() throws InterruptedException {
        MockElapsedTimer timer = new MockElapsedTimer();
        var task = new RoutineTask(0, "Brush Teeth", 1, false, timer);
        task.start();

        String actual, expected;

        actual = task.getTime();
        expected = "00:00";
        assertEquals(expected, actual);

        task.start();
        Thread.sleep(3100);
        actual = task.getTime();
        expected = "00:03";
        assertEquals(expected, actual);
        task.end();

        actual = task.getTime();
        expected = "00:03";
        assertEquals(expected, actual);
    }
}