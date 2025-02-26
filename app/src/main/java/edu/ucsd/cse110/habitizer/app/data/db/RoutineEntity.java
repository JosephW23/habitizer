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

    @ColumnInfo(name = "sortOrder")
    public String sortOrder;

    RoutineEntity(@NonNull String title){
        this.title = title;
    }

    public static RoutineEntity fromRoutine(@NonNull Routine routine){
        var routineentity = new RoutineEntity(routine.title());
        return routineentity;
    }

    public @NonNull Routine toRoutine(){
        return new Routine(id, title, 0);
    }
}