package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RoutineEntity routine);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<RoutineEntity> routines);

    @Query("UPDATE routines SET is_in_progress = :newInProgress WHERE id = :routineId")
    void updateInProgressRoutine(int routineId, boolean newInProgress);

    @Query("UPDATE routines SET is_in_edit = :newInEdit WHERE id = :routineId")
    void updateInEditRoutine(int routineId, boolean newInEdit);

    @Query("SELECT task_elapsed_time FROM routines WHERE id = :routineId")
    int getTaskElapsedTime(int routineId);

    @Query("SELECT * FROM routines ORDER BY sort_order")
    LiveData<List<RoutineEntity>> getRoutineList();

    @Query("SELECT * FROM routines WHERE id = :routineId")
    LiveData<RoutineEntity> getRoutineWithId(int routineId);

    @Query("SELECT * FROM routines WHERE is_in_progress = true")
    LiveData<RoutineEntity> getInProgressRoutine();

    @Query("SELECT * FROM routines WHERE is_in_edit = true")
    LiveData<RoutineEntity> getInEditRoutine();

    @Query("UPDATE routines " +
            "SET routine_elapsed_time = :routineElapsedTime, " +
            "task_elapsed_time = :taskElapsedTime WHERE id = :routineId")
    void updateTime(int routineId, int routineElapsedTime, int taskElapsedTime);

    @Query("UPDATE routines SET goal_time = :newTime WHERE id = :routineId")
    void updateGoalTime(int routineId, int newTime);

    @Query("UPDATE routines SET is_done = :newIsDone WHERE id = :routineId")
    void updateIsDone(int routineId, boolean newIsDone);

    @Query("UPDATE routines SET title = :newTitle WHERE id = :routineId")
    void updateRoutineTitle(int routineId, String newTitle);

    @Query("UPDATE routines SET is_done = false, is_in_progress = false, is_in_edit = false, is_done = false, " +
            "routine_elapsed_time = 0, task_elapsed_time = 0 WHERE id = :routineId")
    void initializeRoutine(int routineId);

    @Query("SELECT COUNT(*) FROM routines")
    int getRoutineCount();

    @Query("SELECT COUNT(*) FROM routines WHERE is_in_progress = true")
    int getInProgressCount();

    @Query("SELECT COUNT(*) FROM routines WHERE is_in_edit = true")
    int getInEditCount();

    @Query("DELETE FROM routines WHERE id = :routineId")
    void deleteRoutine(int routineId);

    @Query("DELETE FROM routines")
    void clearRoutineTable();

}
