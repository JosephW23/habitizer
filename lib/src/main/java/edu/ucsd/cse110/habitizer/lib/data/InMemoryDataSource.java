package edu.ucsd.cse110.habitizer.lib.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.habitizer.lib.domain.RegularTimer;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;

public class InMemoryDataSource {
    private final Map<String, Routine> routines = new HashMap<>();

    public InMemoryDataSource() {};

    // Todo: make this default routine have two routines (Morning and Evening)
    public void initializeDefaultRoutine() {
        RegularTimer timer = new RegularTimer();
        Routine DEFAULT_MORNING_ROUTINE = new Routine("Morning",
                List.of(
                        new RoutineTask(0, "Wake Up", 1, false, timer),
                        new RoutineTask(1,"Eat Breakfast", 2, false, timer),
                        new RoutineTask(2, "Brush Teeth", 3, false, timer)
                ));

        Routine DEFAULT_EVENING_ROUTINE = new Routine("Evening",
                List.of(
                        new RoutineTask(0, "Eat Dinner", 1, false, timer),
                        new RoutineTask(1,"Brush Teeth", 2, false, timer),
                        new RoutineTask(2, "Go To Bed", 3, false, timer)
                ));

        routines.put(DEFAULT_MORNING_ROUTINE.title(), DEFAULT_MORNING_ROUTINE);
        routines.put(DEFAULT_EVENING_ROUTINE.title(), DEFAULT_EVENING_ROUTINE);
    }

    public static InMemoryDataSource fromDefault() {
        var data = new InMemoryDataSource();
        data.initializeDefaultRoutine();
        return data;
    }

    public Routine routine(String name) { return routines.get(name); }

    // Todo: make this method be able to return different list of tasks depending on Routine
    // return List of RoutineTask of Routine object from HashMap (routines).
    public List<RoutineTask> getTaskList(String name) {
        return routines.get(name).tasks();
    }
}
