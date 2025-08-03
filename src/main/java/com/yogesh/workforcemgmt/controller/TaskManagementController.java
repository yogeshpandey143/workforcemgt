package com.yogesh.workforcemgmt.controller;


import com.yogesh.workforcemgmt.dto.*;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;
import com.yogesh.workforcemgmt.model.response.Response;
import com.yogesh.workforcemgmt.service.TaskManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-mgmt")
public class TaskManagementController {
    private final TaskManagementService taskManagementService;


public TaskManagementController(TaskManagementService taskManagementService) {
    this.taskManagementService = taskManagementService;
}


@GetMapping("/{id}")
public Response<TaskManagementDto> getTaskById(@PathVariable Long id) {
    return new Response<>(taskManagementService.findTaskById(id));
}


@PostMapping("/create")
public Response<List<TaskManagementDto>> createTasks(@RequestBody TaskCreateRequest request) {
    return new Response<>(taskManagementService.createTasks(request));
}


@PostMapping("/update")
public Response<List<TaskManagementDto>> updateTasks(@RequestBody UpdateTaskRequest request) {
    return new Response<>(taskManagementService.updateTasks(request));
}


@PostMapping("/assign-by-ref")
public Response<String> assignByReference(@RequestBody AssignByReferenceRequest request) {
    return new Response<>(taskManagementService.assignByReference(request));
}


@PostMapping("/fetch-by-date/v2")
public Response<List<TaskManagementDto>> fetchByDate(@RequestBody TaskFetchByDateRequest request) {
    return new Response<>(taskManagementService.fetchTasksByDate(request));
}

    @PatchMapping("/update-priority/{taskId}")
    public Response<String> updateTaskPriority(
            @PathVariable Long taskId,
            @RequestParam TaskPriority priority
    ) {
        return new Response<>(taskManagementService.updateTaskPriority(taskId, priority));
    }

    @GetMapping("/tasks/priority/{priority}")
    public Response<List<TaskManagementDto>> getTasksByPriority(@PathVariable TaskPriority priority) {
        return new Response<>(taskManagementService.getTasksByPriority(priority));
    }

    @GetMapping("/detail/{id}")
    public Response<TaskDetailsDto> getTaskDetails(@PathVariable Long id) {
        return new Response<>(taskManagementService.getTaskDetails(id));
    }

    @PostMapping("/{taskId}/comment")
    public Response<String> addComment(@PathVariable Long taskId, @RequestBody TaskCommentRequest request) {
        return new Response<>(taskManagementService.addComment(taskId, request));
    }
}


