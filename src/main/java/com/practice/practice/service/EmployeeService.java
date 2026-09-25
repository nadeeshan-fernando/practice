package com.practice.practice.service;

import com.practice.practice.model.dto.ApiResponse;
import com.practice.practice.model.dto.EmployeeDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    ApiResponse<EmployeeDTO> addEmployee(EmployeeDTO dto);
    ApiResponse<List<EmployeeDTO>> getAllEmployees();
    ApiResponse<EmployeeDTO> getEmployeeById(final UUID id);
    ApiResponse<EmployeeDTO> updateEmployeeById(final UUID id, final EmployeeDTO employeeDTO);
    void deleteEmployeeById(UUID id);
    ApiResponse<List<EmployeeDTO>> getEmployeeBySkills(final String skillName);
}
