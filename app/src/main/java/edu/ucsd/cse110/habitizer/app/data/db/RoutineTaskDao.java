package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface RoutineTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(RoutineTaskEntity task);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<RoutineTaskEntity> tasks);

    @Transaction
    default int addTaskToRoutine(RoutineTaskEntity task) {
        return Math.toIntExact(insert(task));
    }

    @Query("SELECT * FROM tasks WHERE routine_id = :routineId")
    LiveData<List<RoutineTaskEntity>> getTaskList(int routineId);

    @Query("SELECT * FROM tasks WHERE id = :id AND routine_id = :routineId")
    LiveData<RoutineTaskEntity> getTaskWithId(int id, int routineId);

    @Query("SELECT is_checked FROM tasks WHERE id = :id AND routine_id = :routineId")
    boolean getIsTaskChecked(int id, int routineId);

    @Query("UPDATE tasks SET title = :newTitle WHERE id = :id AND routine_id = :routineId")
    void updateInTaskTitle(int id, int routineId, String newTitle);

    @Query("UPDATE tasks SET is_checked = false, elapsed_time = '-' WHERE routine_id = :routineId")
    void initializeTask(int routineId);

    @Query("UPDATE tasks SET is_checked = true, elapsed_time = :elapsedTime WHERE id = :id AND routine_id = :routineId")
    void checkOffTask(int id, int routineId, String elapsedTime);

    @Query("SELECT is_checked FROM tasks WHERE routine_id = :routineId")
    List<Boolean> checkIsRoutineDone(int routineId);

    @Query("SELECT COUNT(*) FROM tasks")
    int count();



}
