package com.yogesh.workforcemgmt.repository;

import com.yogesh.workforcemgmt.model.TaskActivity;

import java.util.List;

public interface TaskActivityRepository {
    TaskActivity save(TaskActivity activity);
    List<TaskActivity> findByTaskIdOrderByTimestampAsc(Long taskId);
}
