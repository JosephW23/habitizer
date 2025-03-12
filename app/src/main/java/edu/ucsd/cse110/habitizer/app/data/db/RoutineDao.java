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
    void insert(RoutineEntity routine);

    @Query("SELECT * FROM routines ORDER BY sort_order")
    LiveData<List<RoutineEntity>> findRoutineList();

    @Query("DELETE FROM routines")
    void deleteRoutines();

    @Query("DELETE FROM routines WHERE id==:routineId")
    void deleteRoutine(int routineId);

}
