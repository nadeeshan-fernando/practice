package com.practice.practice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSkillsDTO {
  private long skillId;
  private String skillName;
  private String skillLevel;
}
