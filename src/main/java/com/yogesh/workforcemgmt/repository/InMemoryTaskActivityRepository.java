package com.yogesh.workforcemgmt.repository;

import com.yogesh.workforcemgmt.model.TaskActivity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskActivityRepository implements TaskActivityRepository {

    private final Map<Long, TaskActivity> activityStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public TaskActivity save(TaskActivity activity) {
        if (activity.getId() == null) {
            activity.setId(idCounter.getAndIncrement());
        }
        activityStore.put(activity.getId(), activity);
        return activity;
    }

    @Override
    public List<TaskActivity> findByTaskIdOrderByTimestampAsc(Long taskId) {
        return activityStore.values().stream()
                .filter(a -> a.getTaskId().equals(taskId))
                .sorted((a1, a2) -> Long.compare(a1.getTimestamp(), a2.getTimestamp()))
                .collect(Collectors.toList());
    }
}
