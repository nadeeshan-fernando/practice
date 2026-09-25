package com.practice.practice.service.impl;

import com.practice.practice.exception.ResourceNotFoundException;
import com.practice.practice.model.dto.ApiResponse;
import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.model.entity.Employee;
import com.practice.practice.model.entity.EmployeeSkills;
import com.practice.practice.model.enums.SkillLevel;
import com.practice.practice.repo.EmployeeRepository;
import com.practice.practice.service.EmployeeService;
import com.practice.practice.transformmer.EmployeeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public ApiResponse<EmployeeDTO> addEmployee(EmployeeDTO dto) {
    Employee emp = new Employee();
    emp.setName(dto.getName());
    emp.setDepartment(dto.getDepartment());

    if (dto.getSkills() != null) {
      emp.setSkills(new ArrayList<>());
      for (var s : dto.getSkills()) {
        EmployeeSkills skill = new EmployeeSkills();
        skill.setSkillName(s.getSkillName());
        skill.setSkillLevel(SkillLevel.getLevel(s.getSkillLevel()).name());
        emp.addSkill(skill); // sets employee both sides
      }
    }

    Employee saved = employeeRepository.save(emp);

    return ApiResponse.<EmployeeDTO>builder()
            .success(true)
            .message("Employee created successfully")
            .data(EmployeeTransformer.transformToObject(saved))
            .timestamp(LocalDateTime.now())
            .build();
  }

  @Override
  public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
    // best practice: return [] when empty
    List<EmployeeDTO> employeeDTOList = EmployeeTransformer.transformToList(employeeRepository.findAll());
    return ApiResponse.<List<EmployeeDTO>>builder()
            .success(true)
            .message("Employee fetched successfully")
            .data(employeeDTOList)
            .count(employeeDTOList.size())
            .timestamp(LocalDateTime.now())
            .build();
  }

  @Override
  public ApiResponse<EmployeeDTO> getEmployeeById(final UUID id) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    return ApiResponse.<EmployeeDTO>builder()
            .success(true)
            .message("Employee fetched by id successfully")
            .data(EmployeeTransformer.transformToObject(employee))
            .timestamp(LocalDateTime.now())
            .build();
  }

  @Override
  @Transactional
  public ApiResponse<EmployeeDTO> updateEmployeeById(final UUID id, final EmployeeDTO employeeDTO) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

    employee.setName(employeeDTO.getName());
    employee.setDepartment(employeeDTO.getDepartment());
    return ApiResponse.<EmployeeDTO>builder()
            .success(true)
            .message("Employee updated successfully")
            .data(EmployeeTransformer.transformToObject(employeeRepository.save(employee)))
            .timestamp(LocalDateTime.now())
            .build();
  }

  @Override
  @Transactional
  public void deleteEmployeeById(UUID id) {
    Employee emp = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    employeeRepository.delete(emp); // skills deleted automatically
  }

  @Override
  public ApiResponse<List<EmployeeDTO>> getEmployeeBySkills(final String skillName) {
    List<EmployeeDTO> employeeDTOList = EmployeeTransformer.transformToList(employeeRepository.findEmployeesBySkillFetchSkills(skillName.toUpperCase()));
    return ApiResponse.<List<EmployeeDTO>>builder()
            .success(true)
            .message("Employee fetched by skill successfully")
            .data(employeeDTOList)
            .count(employeeDTOList.size())
            .timestamp(LocalDateTime.now())
            .build();
  }
}
