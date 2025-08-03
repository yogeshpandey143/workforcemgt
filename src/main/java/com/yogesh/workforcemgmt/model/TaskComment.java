package com.yogesh.workforcemgmt.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskComment {

    private Long id;

    private Long taskId;
    private Long userId;
    private String comment;
    private Long timestamp;
}
