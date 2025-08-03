package com.yogesh.workforcemgmt.repository;





import com.yogesh.workforcemgmt.model.TaskManagement;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;

import java.util.List;
import java.util.Optional;


public interface TaskRepository {
    Optional<TaskManagement> findById(Long id);
    TaskManagement save(TaskManagement task);
    List<TaskManagement> findAll();
    List<TaskManagement> findByReferenceIdAndReferenceType(Long referenceId, com.yogesh.workforcemgmt.model.enums.ReferenceType referenceType);
    List<TaskManagement> findByAssigneeIdIn(List<Long> assigneeIds);
    List<TaskManagement> findByPriority(TaskPriority priority);
}

