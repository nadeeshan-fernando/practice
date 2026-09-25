package com.practice.practice.controller;

import com.practice.practice.model.dto.ApiResponse;
import com.practice.practice.model.dto.EmployeeDTO;
import com.practice.practice.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
@Tag(name = "Employee Controller", description = "APIs for employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  public ApiResponse<EmployeeDTO> addEmployee(@RequestBody final EmployeeDTO employeeDTO) {
    return employeeService.addEmployee(employeeDTO); // 201
  }

  @GetMapping
  public ApiResponse<List<EmployeeDTO>> getAllEmployees() {
    return employeeService.getAllEmployees(); // 200 + []
  }

  @GetMapping("/{id}")
  public ApiResponse<EmployeeDTO> getEmployeeById(@PathVariable final UUID id) {
    return employeeService.getEmployeeById(id); // 200 (or 404 via handler)
  }

  @PutMapping("/{id}")
  public ApiResponse<EmployeeDTO> updateEmployeeById(
          @PathVariable final UUID id,
          @RequestBody final EmployeeDTO employeeDTO
  ) {
    return employeeService.updateEmployeeById(id, employeeDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployeeById(@PathVariable final UUID id) {
    employeeService.deleteEmployeeById(id);
    return ResponseEntity.noContent().build(); // 204
  }

  @GetMapping("/search")
  public ApiResponse<List<EmployeeDTO>> getEmployeeBySkills(@RequestParam final String skillName) {
    return employeeService.getEmployeeBySkills(skillName);
  }
}
