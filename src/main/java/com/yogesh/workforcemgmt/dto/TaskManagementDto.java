package com.yogesh.workforcemgmt.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yogesh.workforcemgmt.model.enums.Task;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;
import com.yogesh.workforcemgmt.model.enums.TaskStatus;
import lombok.Data;

import com.yogesh.workforcemgmt.model.enums.ReferenceType;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskManagementDto {
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long taskDeadlineTime;
    private TaskPriority priority;
    private Long createdAt;
}

