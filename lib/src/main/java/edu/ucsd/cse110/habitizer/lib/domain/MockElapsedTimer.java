package edu.ucsd.cse110.habitizer.lib.domain;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.Objects;

public class MockElapsedTimer implements ElapsedTimer {
    private Temporal start;
    private Duration duration;
    private boolean isRunning;

    public MockElapsedTimer() {
        this.duration = Duration.ZERO;
        this.isRunning = false;
        this.start = null;
    }

    @Override
    public void startTimer() {
        if (this.isRunning) return;

        start = LocalTime.now();
        isRunning = true;
    }

    @Override
    public void stopTimer() {
        if (!this.isRunning || Objects.isNull(start)) return;

        this.isRunning = false;
        this.start = null;
        this.duration = Duration.ZERO; // Reset elapsed time when stopping
    }

    @Override
    public String getTime() {
        Duration currDuration = duration;

        if (this.isRunning && Objects.nonNull(start)) {
            currDuration = duration.plus(Duration.between(
                    (LocalTime) start, LocalTime.now()
            ));
        }

        var longSeconds = currDuration.getSeconds();
        int hours = (int)(longSeconds / 3600);
        int minutes = (int)((longSeconds % 3600) / 60);
        int seconds = (int)(longSeconds % 60);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void advanceTimer() {
        if (!isRunning) {
            return; // This prevents advancing while paused
        }
        this.duration = this.duration.plusSeconds(30);
        start = LocalTime.now();
    }

    public void pauseTimer() {
        if (!isRunning || Objects.isNull(start)) return;

        // Properly stop time updates while paused
        duration = duration.plus(Duration.between(
                (LocalTime) start, LocalTime.now()
        ));

        this.isRunning = false;
        this.start = null; // Prevents further time updates
    }

    public void resumeTimer() {
        if (isRunning) return;

        this.start = LocalTime.now();
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
