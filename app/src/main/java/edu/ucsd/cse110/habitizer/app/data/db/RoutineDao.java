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

    @Transaction
    default int add(RoutineEntity routine) {
        var maxSortOrder = getMaxSortOrder();
        var newRoutine = new RoutineEntity(routine.title, maxSortOrder + 1);
        return Math.toIntExact(insert(newRoutine));
    }


    @Query("SELECT * FROM routines ORDER BY sort_order")
    LiveData<List<RoutineEntity>> findRoutineList();

    @Query("SELECT * FROM routines WHERE id = :id")
    LiveData<RoutineEntity> findRoutineWithId(int id);


    @Query("SELECT * FROM routines WHERE is_in_progress = true")
    LiveData<RoutineEntity> findInProgressRoutine();

    @Query("SELECT COUNT(*) FROM routines")
    int count();

    @Query("SELECT MAX(sort_order) FROM routines")
    int getMaxSortOrder();

    @Query("DELETE FROM routines WHERE id = :id")
    void delete(int id);
}
