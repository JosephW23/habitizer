package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.annotation.NonNull;
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

    @Query("SELECT * FROM tasks ORDER BY sort_order WHERE routine_id = :routineId")
    LiveData<List<RoutineTaskEntity>> findTaskList(int routineId);
    @Query("SELECT * FROM tasks WHERE id = :id AND routine_id = :routineId")
    LiveData<RoutineTaskEntity> findTaskWithId(int id, int routineId);



}
