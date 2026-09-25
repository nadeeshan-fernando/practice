package com.practice.practice.service;

import com.practice.practice.model.dto.ApiResponse;

import java.util.List;

public interface EmployeeSkillsService {
    ApiResponse<List<String>> getUniqueSkills();
}
