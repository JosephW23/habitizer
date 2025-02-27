package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;

@Entity(tableName = "routines")
public class RoutineEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "sort_order")
    public int sortOrder;

    @ColumnInfo(name = "is_in_progress")
    public boolean isInProgress;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    @ColumnInfo(name = "routine_elapsed_time")
    public String routineElapsedTime;

    @ColumnInfo(name = "task_elapsed_time")
    public String taskElapsedTime;

    @ColumnInfo(name = "goal_time")
    public String goalTime;


    RoutineEntity(
            String title, int sortOrder, boolean isInProgress, boolean isDone,
            String routineElapsedTime, String taskElapsedTime, String goalTime){
        this.title = title;
        this.sortOrder = sortOrder;
        this.isInProgress = isInProgress;
        this.isDone = isDone;
        this.routineElapsedTime = routineElapsedTime;
        this.taskElapsedTime = taskElapsedTime;
        this.goalTime = goalTime;
    }

    public static RoutineEntity fromRoutine(@NonNull Routine routine){
        var routineEntity = new RoutineEntity(
                routine.title(), routine.sortOrder(),
                routine.isInProgress(), routine.isDone(),
                routine.routineElapsedTime(), routine.taskElapsedTime(), routine.goalTime());
        return routineEntity;
    }

    public @NonNull Routine toRoutine(){
        return new Routine(id, title, sortOrder, isInProgress, isDone,
                routineElapsedTime, taskElapsedTime, goalTime);
    }
}