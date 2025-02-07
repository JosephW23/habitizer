package edu.ucsd.cse110.habitizer.app.routine;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MorningRoutinePresenterTest {

    private MorningRoutinePresenter presenter;
    private RoutineContract.View mockView;

    @Before
    public void setUp() {
        mockView = mock(RoutineContract.View.class);
        List<String> tasks = Arrays.asList("Exercise", "Meditation", "Reading");
        presenter = new MorningRoutinePresenter(mockView, tasks);
    }

    @Test
    public void testLoadTasks_callsDisplayTasks() {
        presenter.loadTasks();
        verify(mockView).displayTasks(new String[]{"Exercise", "Meditation", "Reading"});
    }
}
