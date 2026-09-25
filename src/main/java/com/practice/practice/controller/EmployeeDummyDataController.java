package com.practice.practice.controller;

import com.practice.practice.service.EmployeeDummyDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Dummy Data Controller", description = "API for generate dummy data")
public class EmployeeDummyDataController {

    private final EmployeeDummyDataService employeeDummyDataService;

    @PostMapping("/dummy")
    public ResponseEntity<String> generateDummyEmployees(
            @RequestParam(defaultValue = "20") int count) {

        String response = employeeDummyDataService.generateDummyEmployees(count);
        return ResponseEntity.ok(response);
    }
}
