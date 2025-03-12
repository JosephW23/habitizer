package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.habitizer.lib.domain.Routine;

@Entity(tableName = "routines")
public class RoutineEntity {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    public final Integer id;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "sort_order")
    public final int sortOrder;

    @ColumnInfo(name = "is_in_progress")
    public final boolean isInProgress;

    @ColumnInfo(name = "is_in_edit")
    public final boolean isInEdit;

    @ColumnInfo(name = "is_done")
    public final boolean isDone;

    @ColumnInfo(name = "is_paused")
    public final boolean isPaused;

    @ColumnInfo(name = "routine_elapsed_time")
    public final int routineElapsedTime;

    @ColumnInfo(name = "task_elapsed_time")
    public final int taskElapsedTime;

    @ColumnInfo(name = "goal_time")
    public final int goalTime;


    RoutineEntity(
            int id, String title, int sortOrder, boolean isInProgress, boolean isInEdit, boolean isDone, boolean isPaused,
            int routineElapsedTime, int taskElapsedTime, int goalTime){
        this.id = id;
        this.title = title;
        this.sortOrder = sortOrder;
        this.isInProgress = isInProgress;
        this.isInEdit = isInEdit;
        this.isDone = isDone;
        this.isPaused = isPaused;
        this.routineElapsedTime = routineElapsedTime;
        this.taskElapsedTime = taskElapsedTime;
        this.goalTime = goalTime;
    }

    public static RoutineEntity fromRoutine(@NonNull Routine routine){
        return new RoutineEntity(
                routine.id(), routine.title(), routine.sortOrder(),
                routine.isInProgress(), routine.isInEdit(), routine.isDone(), routine.isPaused(),
                routine.routineElapsedTime(), routine.taskElapsedTime(), routine.goalTime());
    }

    public Routine toRoutine(){
        return new Routine(id, title, sortOrder, isInProgress, isInEdit, isDone, isPaused,
                routineElapsedTime, taskElapsedTime, goalTime);
    }
}