package com.yogesh.workforcemgmt.repository;

import com.yogesh.workforcemgmt.model.TaskComment;

import java.util.List;

public interface TaskCommentRepository {
    TaskComment save(TaskComment comment);
    List<TaskComment> findByTaskIdOrderByTimestampAsc(Long taskId);
}
