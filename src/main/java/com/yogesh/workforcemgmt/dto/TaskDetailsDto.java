package com.yogesh.workforcemgmt.dto;

import com.yogesh.workforcemgmt.model.TaskActivity;
import com.yogesh.workforcemgmt.model.TaskComment;
import com.yogesh.workforcemgmt.model.TaskManagement;
import lombok.Data;

import java.util.List;

@Data
public class TaskDetailsDto {
    private TaskManagement task;
    private List<TaskComment> comments;
    private List<TaskActivity> activities;
}
