package com.yogesh.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import com.yogesh.workforcemgmt.model.enums.ReferenceType;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AssignByReferenceRequest {

    private Long referenceId;
    private ReferenceType referenceType;
    private Long assigneeId;
}
