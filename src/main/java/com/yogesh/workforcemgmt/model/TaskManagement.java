package com.yogesh.workforcemgmt.model;
import com.yogesh.workforcemgmt.model.enums.Task;
import com.yogesh.workforcemgmt.model.enums.TaskPriority;
import com.yogesh.workforcemgmt.model.enums.TaskStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.yogesh.workforcemgmt.model.enums.ReferenceType;



@Data
@Getter
@Setter
public class TaskManagement {
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
