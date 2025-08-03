package com.yogesh.workforcemgmt.service.impl;

import com.yogesh.workforcemgmt.dto.*;
import com.yogesh.workforcemgmt.exception.ResourceNotFoundException;
import com.yogesh.workforcemgmt.mapper.ITaskManagementMapper;
import com.yogesh.workforcemgmt.model.TaskActivity;
import com.yogesh.workforcemgmt.model.TaskComment;
import com.yogesh.workforcemgmt.model.TaskManagement;
import com.yogesh.workforcemgmt.model.enums.Task;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;
import com.yogesh.workforcemgmt.model.enums.TaskStatus;
import com.yogesh.workforcemgmt.repository.TaskActivityRepository;
import com.yogesh.workforcemgmt.repository.TaskCommentRepository;
import com.yogesh.workforcemgmt.repository.TaskRepository;
import com.yogesh.workforcemgmt.service.TaskManagementService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskManagementServiceImpl implements TaskManagementService {


    private final TaskRepository taskRepository;
    private final ITaskManagementMapper taskMapper;
    private final TaskCommentRepository taskCommentRepository;
    private final TaskActivityRepository taskActivityRepository;



    public TaskManagementServiceImpl(TaskRepository taskRepository, ITaskManagementMapper taskMapper,  TaskCommentRepository taskCommentRepository,
                                     TaskActivityRepository taskActivityRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskCommentRepository = taskCommentRepository;
        this.taskActivityRepository = taskActivityRepository;
    }


    @Override
    public TaskManagementDto findTaskById(Long id) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return taskMapper.modelToDto(task);
    }


    @Override
    public List<TaskManagementDto> createTasks(TaskCreateRequest createRequest) {
        List<TaskManagement> createdTasks = new ArrayList<>();
        for (TaskCreateRequest.RequestItem item : createRequest.getRequests()) {
            TaskManagement newTask = new TaskManagement();
            newTask.setReferenceId(item.getReferenceId());
            newTask.setReferenceType(item.getReferenceType());
            newTask.setTask(item.getTask());
            newTask.setAssigneeId(item.getAssigneeId());
            newTask.setPriority(item.getPriority());
            newTask.setTaskDeadlineTime(item.getTaskDeadlineTime());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("New task created.");
            newTask.setCreatedAt(System.currentTimeMillis());
            createdTasks.add(taskRepository.save(newTask));
        }
        return taskMapper.modelListToDtoList(createdTasks);
    }


    @Override
    public List<TaskManagementDto> updateTasks(UpdateTaskRequest updateRequest) {
        List<TaskManagement> updatedTasks = new ArrayList<>();
        for (UpdateTaskRequest.RequestItem item : updateRequest.getRequests()) {
            TaskManagement task = taskRepository.findById(item.getTaskId())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + item.getTaskId()));


            if (item.getTaskStatus() != null) {
                task.setStatus(item.getTaskStatus());
            }
            if (item.getDescription() != null) {
                task.setDescription(item.getDescription());
            }
            updatedTasks.add(taskRepository.save(task));
        }
        return taskMapper.modelListToDtoList(updatedTasks);
    }


    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        List<Task> applicableTasks = Task.getTasksByReferenceType(request.getReferenceType());
        List<TaskManagement> existingTasks = taskRepository.findByReferenceIdAndReferenceType(request.getReferenceId(), request.getReferenceType());


        for (Task taskType : applicableTasks) {
            List<TaskManagement> tasksOfType = existingTasks.stream()
                    .filter(t -> t.getTask() == taskType && t.getStatus() != TaskStatus.COMPLETED)
                    .collect(Collectors.toList());


            // BUG #1 is here. It should assign one and cancel the rest.
            // Instead, it reassigns ALL of them.

            for (TaskManagement oldTask : tasksOfType) {
                oldTask.setStatus(TaskStatus.CANCELLED);
                oldTask.setDescription("Cancelled due to reassignment.");
                taskRepository.save(oldTask);
            }

//            if (!tasksOfType.isEmpty()) {
//                for (TaskManagement taskToUpdate : tasksOfType) {
//                    taskToUpdate.setAssigneeId(request.getAssigneeId());
//                    taskRepository.save(taskToUpdate);
//                }
//            } else {
                // Create a new task if none exist
                TaskManagement newTask = new TaskManagement();
                newTask.setReferenceId(request.getReferenceId());
                newTask.setReferenceType(request.getReferenceType());
                newTask.setTask(taskType);
                newTask.setAssigneeId(request.getAssigneeId());
                newTask.setStatus(TaskStatus.ASSIGNED);
                taskRepository.save(newTask);
            }
//        }
        return "Tasks assigned successfully for reference " + request.getReferenceId();
    }


    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        List<TaskManagement> tasks = taskRepository.findByAssigneeIdIn(request.getAssigneeIds());


        // BUG #2 is here. It should filter out CANCELLED tasks but doesn't.
        List<TaskManagement> filteredTasks = tasks.stream()
                .filter(task ->
                        // Condition 2: Created before the range but still open
                        task.getStatus() != TaskStatus.CANCELLED &&
                                task.getStatus() != TaskStatus.COMPLETED &&
                                (
                                        // Condition 1: Created within the date range
                                        task.getCreatedAt() < request.getStartDate() || task.getCreatedAt() <= request.getEndDate()
                                )
                )
                .collect(Collectors.toList());



        return taskMapper.modelListToDtoList(filteredTasks);
    }

    @Override
    public String updateTaskPriority(Long taskId, TaskPriority priority) {
        TaskManagement task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        task.setPriority(priority);
        taskRepository.save(task);

        logActivity(taskId, "Priority updated to " + priority);
        return "Priority updated successfully for task ID: " + taskId;
    }

    @Override
    public List<TaskManagementDto> getTasksByPriority(TaskPriority priority) {
        List<TaskManagement> tasks = taskRepository.findByPriority(priority);
        return taskMapper.modelListToDtoList(tasks);
    }

    @Override
    public String addComment(Long taskId, TaskCommentRequest request) {
        TaskComment comment = new TaskComment();
        comment.setTaskId(taskId);
        comment.setUserId(request.getUserId());
        comment.setComment(request.getComment());
        comment.setTimestamp(System.currentTimeMillis());
        taskCommentRepository.save(comment);

        logActivity(taskId, "User " + request.getUserId() + " commented: \"" + request.getComment() + "\"");
        return "Comment added";
    }

    @Override
    public TaskDetailsDto getTaskDetails(Long id) {
        TaskManagement task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        List<TaskComment> comments = taskCommentRepository.findByTaskIdOrderByTimestampAsc(id);
        List<TaskActivity> activities = taskActivityRepository.findByTaskIdOrderByTimestampAsc(id);

        TaskDetailsDto dto = new TaskDetailsDto();
        dto.setTask(task);
        dto.setComments(comments);
        dto.setActivities(activities);

        return dto;
    }


    private void logActivity(Long taskId, String message) {
        TaskActivity activity = new TaskActivity();
        activity.setTaskId(taskId);
        activity.setActivity(message);
        activity.setTimestamp(System.currentTimeMillis());
        taskActivityRepository.save(activity);
    }
}
