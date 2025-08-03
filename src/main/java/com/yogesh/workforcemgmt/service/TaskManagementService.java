package com.yogesh.workforcemgmt.service;

import com.yogesh.workforcemgmt.dto.*;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;

import java.util.List;

public interface TaskManagementService {
    List<TaskManagementDto> createTasks(TaskCreateRequest request);
    List<TaskManagementDto> updateTasks(UpdateTaskRequest request);
    String assignByReference(AssignByReferenceRequest request);
    String updateTaskPriority(Long taskId, TaskPriority priority);
    List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request);
    TaskManagementDto findTaskById(Long id);
    List<TaskManagementDto> getTasksByPriority(TaskPriority priority);
    String addComment(Long taskId, TaskCommentRequest request);
    TaskDetailsDto getTaskDetails(Long id);


}

