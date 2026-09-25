package com.practice.practice.controller;

import com.practice.practice.model.dto.ApiResponse;
import com.practice.practice.service.EmployeeSkillsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
@Tag(name = "EmployeeSkills Controller", description = "APIs for skill")
public class EmployeeSkillsController {
  final EmployeeSkillsService employeeSkillsService;

  @GetMapping("/all")
  private ApiResponse<List<String>> getUniqueSkills(){
    return employeeSkillsService.getUniqueSkills();
  }
}
