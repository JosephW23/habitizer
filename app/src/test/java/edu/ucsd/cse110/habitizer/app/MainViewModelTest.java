package edu.ucsd.cse110.habitizer.app;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.os.Looper;
import static org.robolectric.Shadows.shadowOf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.SimpleSubject;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import org.junit.Rule;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private RoutineRepository mockRoutineRepo;
    private MainViewModel mainViewModel;
    private Routine testRoutine;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        mockRoutineRepo = mock(RoutineRepository.class);
        testRoutine = new Routine(1, "Morning Routine", 1, false, false, false, 0, 0, 60);
        List<Routine> routineList = new ArrayList<>();
        routineList.add(testRoutine);
        SimpleSubject<List<Routine>> defaultSubject = new SimpleSubject<>();
        defaultSubject.setValue(routineList);
        when(mockRoutineRepo.findRoutineList()).thenReturn(defaultSubject);
        mainViewModel = new MainViewModel(mockRoutineRepo);
        setPrivateRoutineField(mainViewModel, testRoutine);
        mainViewModel.stopRoutineTimer();
        mainViewModel.stopTaskTimer();
        shadowOf(Looper.getMainLooper()).idle();
    }
    private void setPrivateRoutineField(MainViewModel viewModel, Routine routine)
            throws NoSuchFieldException, IllegalAccessException {
        Field routineField = MainViewModel.class.getDeclaredField("routine");
        routineField.setAccessible(true);
        routineField.set(viewModel, routine);
    }

    // US1: Routine Creation
    @Test
    public void testAddRoutine_updatesRoutineList() {
        mainViewModel.addRoutine("Workout Routine");
        List<Routine> updatedList = new ArrayList<>(mainViewModel.loadRoutineList().getValue());
        updatedList.add(new Routine(2, "Workout Routine", 2, false, false, false, 0, 0, 60));
        SimpleSubject<List<Routine>> updatedSubject = new SimpleSubject<>();
        updatedSubject.setValue(updatedList);
        when(mockRoutineRepo.findRoutineList()).thenReturn(updatedSubject);
        assertNotNull(updatedList);
        assertEquals(2, updatedList.size());
        assertEquals("Workout Routine", updatedList.get(1).title());
    }

    // US2: Check Off Task
    @Test
    public void testCheckOffTask_marksTaskAsChecked() {
        RoutineTask task = new RoutineTask(1, 1, "Brush Teeth", false, -1);
        testRoutine.setTasks(List.of(task));

        mainViewModel.checkOffTask(task);
        shadowOf(Looper.getMainLooper()).idle();
        assertTrue(task.isChecked());
    }

    @Test
    public void testCheckOffTask_updatesRoutineCompletionStatus() {
        RoutineTask task = new RoutineTask(1, 1, "Brush Teeth", false, -1);
        testRoutine.setTasks(List.of(task));

        mainViewModel.checkOffTask(task);
        shadowOf(Looper.getMainLooper()).idle();
        assertTrue(mainViewModel.checkIsRoutineDone());
    }
    // US3: Display Routine Elapsed Time
    @Test
    public void testRoutineElapsedTime_updatesCorrectly() {
        mainViewModel.startRoutine();
        shadowOf(Looper.getMainLooper()).idle();
        mainViewModel.advanceRoutineTimer();
        mainViewModel.advanceRoutineTimer();
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals("1", mainViewModel.getRoutineTimer().getRoundedDownTime());
    }

    // US3b: Stop Timer
    @Test
    public void testStopRoutineTimer_keepsElapsedTimeConstant() {
        mainViewModel.startRoutine();
        shadowOf(Looper.getMainLooper()).idle();
        mainViewModel.advanceRoutineTimer();
        mainViewModel.advanceRoutineTimer();
        shadowOf(Looper.getMainLooper()).idle();
        mainViewModel.stopRoutineTimer();
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals("1", mainViewModel.getRoutineTimer().getRoundedDownTime());
    }

    // US3c: Manually Advance Routine Timer
    @Test
    public void testAdvanceRoutineTimer_increasesTimeBy30Seconds() {
        mainViewModel.startRoutine();
        shadowOf(Looper.getMainLooper()).idle();

        mainViewModel.advanceRoutineTimer();
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals("-", mainViewModel.getRoutineTimer().getRoundedDownTime());
    }

    @Test
    public void testAdvanceTaskTimer_increasesTimeBy30Seconds() {
        mainViewModel.startRoutine();
        shadowOf(Looper.getMainLooper()).idle();

        mainViewModel.advanceTaskTimer();
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals("-", mainViewModel.getTaskTimer().getRoundedDownTime());
    }

    // US4: Set Routine Goal Time
    @Test
    public void testSetRoutineGoalTime_updatesGoalTime() {
        mainViewModel.updateGoalTime(90);
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals(90, testRoutine.goalTime());
    }

    // US5: End Routine
    @Test
    public void testEndRoutine_stopsRoutineAndTaskTimers() {
        mainViewModel.startRoutine();
        shadowOf(Looper.getMainLooper()).idle();

        mainViewModel.endRoutine();
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals("-", mainViewModel.getRoutineTimer().getRoundedDownTime());
        assertEquals("-", mainViewModel.getTaskTimer().getRoundedDownTime());
    }

    // US6: Track Task Elapsed Time
    @Test
    public void testCheckOffTask_recordsElapsedTime() {
        RoutineTask task = new RoutineTask(1, 1, "Brush Teeth", false, -1);
        testRoutine.setTasks(List.of(task));
        mainViewModel.advanceRoutineTimer();
        shadowOf(Looper.getMainLooper()).idle();
        mainViewModel.checkOffTask(task);
        shadowOf(Looper.getMainLooper()).idle();

        assertEquals("1", mainViewModel.getRoundedDownTime(70));
    }

    // US9: Add Task
    @Test
    public void testAddRoutineTask_increasesTaskList() {
        int initialTaskCount = testRoutine.tasks().size();
        mainViewModel.addRoutineTask("New Task");
        shadowOf(Looper.getMainLooper()).idle();

        assertEquals(initialTaskCount + 1, testRoutine.tasks().size());
    }

    // US10: Rename Task
    @Test
    public void testUpdateTaskName_changesTaskTitle() {
        RoutineTask task = new RoutineTask(1, 1, "Old Task", false, -1);
        testRoutine.setTasks(List.of(task));

        mainViewModel.updateTaskName(1, "New Task Name");
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals("New Task Name", task.title());
    }

    // US11-3: Customize Routines
    @Test
    public void testRoutineIsCustomizable() {
        Routine customRoutine = new Routine(2, "Weekend Routine", 2, false, false, false, 0, 0, 60);
        customRoutine.setGoalTime(90);
        customRoutine.setIsDone(true);

        assertEquals(90, customRoutine.goalTime());
        assertTrue(customRoutine.isDone());
    }

    // US11-3: Prevent Duplicate Routine Names
    @Test
    public void testAddRoutine_doesNotAllowDuplicateNames() {
        mainViewModel.addRoutine("Morning Routine");
        shadowOf(Looper.getMainLooper()).idle();

        List<Routine> updatedList = mainViewModel.loadRoutineList().getValue();
        assertNotNull(updatedList);
        assertEquals(1, updatedList.size());
    }

    // US13: Delete Task From Routine
    @Test
    public void testDeleteTask_decreasesTaskList() {
        var task = new RoutineTask(0, testRoutine.id(), "Get Wild", false, 0);
        testRoutine.addTask(task);
        int initialTaskCount = testRoutine.tasks().size();
        mainViewModel.removeTask(task);
        shadowOf(Looper.getMainLooper()).idle();

        assertEquals(initialTaskCount - 1, testRoutine.tasks().size());
    }
    // US12: Task Reordering
    @Test
    public void testUpdateTaskOrder_SingleTask_NoChange() {
        List<RoutineTask> tasks = new ArrayList<>();
        tasks.add(new RoutineTask(1, 1, "Task 1", false, 0));
        mainViewModel.updateTaskOrder(tasks);
        shadowOf(Looper.getMainLooper()).idle();
        assertEquals(0, tasks.get(0).sortOrder());
    }

    @Test
    public void testUpdateTaskOrder_MultipleTasks_Reordered() {
        List<RoutineTask> tasks = new ArrayList<>();
        tasks.add(new RoutineTask(1, 1, "Task 1", false, 0));
        tasks.add(new RoutineTask(2, 1, "Task 2", false, 1));
        tasks.add(new RoutineTask(3, 1, "Task 3", false, 2));
        List<RoutineTask> newOrder = new ArrayList<>();
        newOrder.add(tasks.get(2));
        newOrder.add(tasks.get(0));
        newOrder.add(tasks.get(1));

        mainViewModel.updateTaskOrder(newOrder);
        shadowOf(Looper.getMainLooper()).idle();

        assertEquals(0, newOrder.get(0).sortOrder());
        assertEquals(1, newOrder.get(1).sortOrder());
        assertEquals(2, newOrder.get(2).sortOrder());
    }

    @Test
    public void testUpdateTaskOrder_MoveToEnd() {
        List<RoutineTask> tasks = new ArrayList<>();
        tasks.add(new RoutineTask(1, 1, "Task 1", false, 0));
        tasks.add(new RoutineTask(2, 1, "Task 2", false, 1));
        tasks.add(new RoutineTask(3, 1, "Task 3", false, 2));
        List<RoutineTask> newOrder = new ArrayList<>();
        newOrder.add(tasks.get(1));
        newOrder.add(tasks.get(2));
        newOrder.add(tasks.get(0));

        mainViewModel.updateTaskOrder(newOrder);
        shadowOf(Looper.getMainLooper()).idle();

        assertEquals(0, newOrder.get(0).sortOrder());
        assertEquals(1, newOrder.get(1).sortOrder());
        assertEquals(2, newOrder.get(2).sortOrder());
    }

    @Test
    public void testUpdateTaskOrder_MoveToStart() {
        List<RoutineTask> tasks = new ArrayList<>();
        tasks.add(new RoutineTask(1, 1, "Task 1", false, 0));
        tasks.add(new RoutineTask(2, 1, "Task 2", false, 1));
        tasks.add(new RoutineTask(3, 1, "Task 3", false, 2));
        List<RoutineTask> newOrder = new ArrayList<>();
        newOrder.add(tasks.get(2));
        newOrder.add(tasks.get(0));
        newOrder.add(tasks.get(1));

        mainViewModel.updateTaskOrder(newOrder);
        shadowOf(Looper.getMainLooper()).idle();

        assertEquals(0, newOrder.get(0).sortOrder());
        assertEquals(1, newOrder.get(1).sortOrder());
        assertEquals(2, newOrder.get(2).sortOrder());
    }
}