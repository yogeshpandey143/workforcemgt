package com.yogesh.workforcemgmt.dto;



import lombok.Data;

@Data
public class TaskCommentRequest {
    private Long userId;
    private String comment;
}