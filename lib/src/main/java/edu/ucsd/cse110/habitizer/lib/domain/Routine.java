package edu.ucsd.cse110.habitizer.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public record Routine(@NonNull int id, @NonNull String title, @NonNull List<RoutineTask> tasks) {
    public Routine(@Nullable int id, @NonNull String title, List<RoutineTask> tasks) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Routine title cannot be blank");
        }
        this.id = id;
        this.title = title;
        this.tasks = List.copyOf(tasks);
    }
}
