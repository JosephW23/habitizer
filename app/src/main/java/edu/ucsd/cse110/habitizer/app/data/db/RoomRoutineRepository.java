package edu.ucsd.cse110.habitizer.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.habitizer.app.util.LiveDataSubjectAdapter;
import edu.ucsd.cse110.habitizer.lib.domain.Routine;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineTask;
import edu.ucsd.cse110.habitizer.lib.util.Subject;

public class RoomRoutineRepository implements RoutineRepository {
    private final RoutineDao routineDao;
    private final RoutineTaskDao routineTaskDao;

    public RoomRoutineRepository(RoutineDao routineDao, RoutineTaskDao routineTaskDao) {
        this.routineDao = routineDao;
        this.routineTaskDao = routineTaskDao;
    }

    @Override
    public Subject<List<Routine>> getRoutineList() {
        var entitiesLiveData = routineDao.findAllAsLiveData();
        var routineLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(RoutineEntity::toRoutine)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(routineLiveData);
    }

    // return a List of RoutineTask
    @Override
    public Subject<List<RoutineTask>> getTaskList(String name) {

    };

    RoutineTask getTaskWithIdandName(String name, int id);

    // Add a new task to a routine
    void addTaskToRoutine(String routineName, String taskName);

    @Override
    public Subject<Routine> find(int id) {
        LiveData<RoutineEntity> entityLiveData = flashcardDao.findAsLiveData(id);
        LiveData<Routine> flashcardLiveData = Transformations.map(entityLiveData, RoutineEntity::toRoutine);
        return new LiveDataSubjectAdapter<>(flashcardLiveData);
    }

    @Override
    public Subject<List<Routine>> findAll() {
        var entitiesLiveData = flashcardDao.findAllAsLiveData();
        var flashcardLiveData = Transformations.map(entitiesLiveData, entities -> {
            return entities.stream()
                    .map(RoutineEntity::toRoutine)
                    .collect(Collectors.toList());
        });
        return new LiveDataSubjectAdapter<>(flashcardLiveData);
    }

    @Override
    public void save(Routine flashcard) {
        flashcardDao.insert(RoutineEntity.fromRoutine(flashcard));
    }

    @Override
    public void save(List<Routine> flashcards) {
        var entities = flashcards.stream()
                .map(RoutineEntity::fromRoutine)
                .collect(Collectors.toList());
        flashcardDao.insert(entities);
    }

    @Override
    public void append(Routine flashcard) {
        flashcardDao.append(RoutineEntity.fromRoutine(flashcard));
    }

    @Override
    public void prepend(Routine flashcard) {
        flashcardDao.prepend(RoutineEntity.fromRoutine(flashcard));
    }

    @Override
    public void remove(int id) {
        flashcardDao.delete(id);
    }
}

