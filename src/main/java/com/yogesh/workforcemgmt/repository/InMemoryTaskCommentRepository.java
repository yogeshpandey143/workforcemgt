package com.yogesh.workforcemgmt.repository;

import com.yogesh.workforcemgmt.model.TaskComment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryTaskCommentRepository implements TaskCommentRepository {

    private final Map<Long, TaskComment> commentStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public TaskComment save(TaskComment comment) {
        if (comment.getId() == null) {
            comment.setId(idCounter.getAndIncrement());
        }
        commentStore.put(comment.getId(), comment);
        return comment;
    }

    @Override
    public List<TaskComment> findByTaskIdOrderByTimestampAsc(Long taskId) {
        return commentStore.values().stream()
                .filter(c -> c.getTaskId().equals(taskId))
                .sorted((c1, c2) -> Long.compare(c1.getTimestamp(), c2.getTimestamp()))
                .collect(Collectors.toList());
    }
}
