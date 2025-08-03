package com.yogesh.workforcemgmt.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TaskActivity {

    private Long id;

    private Long taskId;
    private String activity;
    private Long timestamp;
}