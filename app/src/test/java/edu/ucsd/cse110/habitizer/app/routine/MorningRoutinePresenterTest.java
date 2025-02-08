package edu.ucsd.cse110.habitizer.app.routine;

import static org.mockito.Mockito.*;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MorningRoutinePresenterTest {

    private MorningRoutinePresenter presenter;
    private RoutineContract.View mockView;
    private RoutineRepository mockRepository;

    @Before
    public void setUp() {
        mockView = mock(RoutineContract.View.class);
        mockRepository = mock(RoutineRepository.class);
        Routine mockRoutine = new Routine("Test Routine", List.of(
                new RoutineTask("Exercise", 1, false),
                new RoutineTask("Meditation", 2, false),
                new RoutineTask("Reading", 3, false)
        ));

        when(mockRepository.routine("Test Routine")).thenReturn(mockRoutine);
        presenter = new MorningRoutinePresenter(mockView, mockRepository);
    }

    @Test
    public void testLoadTasks_callsDisplayTasks() {
        presenter.loadTasks();
        verify(mockView).displayTasks(List.of(
                new RoutineTask("Exercise", 1, false),
                new RoutineTask("Meditation", 2, false),
                new RoutineTask("Reading", 3, false)
        ));
    }
}
