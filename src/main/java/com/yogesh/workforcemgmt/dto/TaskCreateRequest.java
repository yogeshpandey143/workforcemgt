package com.yogesh.workforcemgmt.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yogesh.workforcemgmt.model.enums.Task;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;
import lombok.Data;

import com.yogesh.workforcemgmt.model.enums.ReferenceType;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskCreateRequest {
    private List<RequestItem> requests;
    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RequestItem {
        private Long referenceId;
        private ReferenceType referenceType;
        private Task task;
        private Long assigneeId;
        private TaskPriority priority;
        private Long taskDeadlineTime;
    }
}
