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
    default int add(RoutineTaskEntity task) {
        var maxSortOrder = getMaxSortOrder();
        var newTask = new RoutineTaskEntity(task.routineId, task.title,
                task.isChecked, task.elapsedTime, maxSortOrder + 1);
        return Math.toIntExact(insert(newTask));
    }

    @Query("SELECT * FROM tasks WHERE id = :id")
    RoutineTaskEntity find(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    List<RoutineTaskEntity> findAll();

    @Query("SELECT * FROM tasks WHERE routine_id = :routineId")
    List<RoutineTaskEntity> findAllWithId(int routineId);

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<RoutineTaskEntity> findAsLiveData(int id);

    @Query("SELECT * FROM tasks ORDER BY sort_order")
    LiveData<List<RoutineTaskEntity>> findAllAsLiveData();

    @Query("SELECT * FROM tasks WHERE routine_id = :routineId")
    List<RoutineTaskEntity> findAllAsLiveDataWithId(int routineId);

    @Query("SELECT COUNT(*) FROM tasks")
    int count();

    @Query("SELECT MAX(sort_order) FROM tasks")
    int getMaxSortOrder();

    @Query("DELETE FROM tasks WHERE id = :id")
    void delete(int id);
}
