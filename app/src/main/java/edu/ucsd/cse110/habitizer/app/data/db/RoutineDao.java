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

    @Query("SELECT task_elapsed_time FROM routines WHERE id = :routineId")
    String getTaskElapsedTime(int routineId);

    @Query("SELECT * FROM routines ORDER BY sort_order")
    LiveData<List<RoutineEntity>> getRoutineList();

    @Query("SELECT * FROM routines WHERE id = :routineId")
    LiveData<RoutineEntity> getRoutineWithId(int routineId);

    @Query("SELECT * FROM routines WHERE is_in_progress = true")
    LiveData<RoutineEntity> getInProgressRoutine();

    @Query("UPDATE routines " +
            "SET routine_elapsed_time = :routineElapsedTime, " +
            "task_elapsed_time = :taskElapsedTime WHERE id = :routineId")
    void updateTime(int routineId, String routineElapsedTime, String taskElapsedTime);

    @Query("UPDATE routines SET goal_time = :newTime WHERE id = :routineId")
    void updateGoalTime(int routineId, String newTime);

    @Query("UPDATE routines SET is_done = :newIsDone WHERE id = :routineId")
    void updateIsDone(int routineId, boolean newIsDone);

    @Query("UPDATE routines SET is_done = false, is_in_progress = false, is_done = false, " +
            "routine_elapsed_time = '-', task_elapsed_time = '-' WHERE id = :routineId")
    void initializeRoutine(int routineId);

    @Query("SELECT COUNT(*) FROM routines")
    int getRoutineCount();

    @Query("SELECT COUNT(*) FROM routines WHERE is_in_progress = true")
    int getInProgressCount();

    @Query("DELETE FROM routines WHERE id = :routineId")
    void deleteRoutine(int routineId);

    @Query("DELETE FROM routines")
    void clearRoutineTable();

}
