package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

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
    LiveData<List<RoutineEntity>> findRoutineList();

    @Query("SELECT * FROM routines WHERE id = :id")
    LiveData<RoutineEntity> findRoutineWithId(int id);

    @Query("SELECT * FROM routines WHERE is_in_progress = true")
    LiveData<RoutineEntity> findInProgressRoutine();

    @Query("UPDATE routines " +
            "SET routine_elapsed_time = :routineElapsedTime, " +
            "task_elapsed_time = :taskElapsedTime WHERE id = :routineId")
    void updateTime(int routineId, String routineElapsedTime, String taskElapsedTime);

    @Query("UPDATE routines SET goal_time = :newTime WHERE id = :routineId")
    void updateGoalTime(int routineId, String newTime);

    @Query("UPDATE routines SET is_done = :newIsDone WHERE id = :routineId")
    void updateIsDone(int routineId, boolean newIsDone);

    @Query("SELECT COUNT(*) FROM routines")
    int count();

}
