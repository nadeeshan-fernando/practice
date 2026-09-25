package com.practice.practice.service.impl;

import com.practice.practice.model.entity.Employee;
import com.practice.practice.model.entity.EmployeeSkills;
import com.practice.practice.model.enums.SkillLevel;
import com.practice.practice.repo.EmployeeRepository;
import com.practice.practice.service.EmployeeDummyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmployeeDummyDataServiceImpl implements EmployeeDummyDataService {

    private final EmployeeRepository employeeRepository;

    private static final String[] NAMES = {
            "Anton", "Nimal", "Kamal", "Saman", "Kasun",
            "Ravi", "Tharindu", "Ishara", "Reshan", "Dilshan",
            "Ayesha", "Nadee", "Shalini", "Kavindi", "Umasha"
    };

    private static final String[] DEPARTMENTS = {
            "IT", "HR", "Finance", "QA", "Operations", "Support"
    };

    private static final String[] SKILLS = {
            "Java", "Spring Boot", "SQL", "React", "Angular",
            "Docker", "AWS", "Python", "Microservices", "Git"
    };

    private final Random random = new Random();

    @Override
    @Transactional
    public String generateDummyEmployees(int count) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            Employee employee = new Employee();
            employee.setName(generateName(i));
            employee.setDepartment(randomDepartment());

            int skillCount = 2 + random.nextInt(4); // 2 to 5 skills
            for (int j = 0; j < skillCount; j++) {
                EmployeeSkills skill = new EmployeeSkills();
                skill.setSkillName(randomSkill().toUpperCase());
                skill.setSkillLevel(randomSkillLevel().getLevelLabel().toUpperCase());

                employee.addSkill(skill);
            }

            employees.add(employee);
        }

        employeeRepository.deleteAll();
        employeeRepository.saveAll(employees);
        return count + " dummy employees inserted successfully";
    }

    private String generateName(int index) {
        return NAMES[random.nextInt(NAMES.length)] + " " + index;
    }

    private String randomDepartment() {
        return DEPARTMENTS[random.nextInt(DEPARTMENTS.length)];
    }

    private String randomSkill() {
        return SKILLS[random.nextInt(SKILLS.length)];
    }

    private SkillLevel randomSkillLevel() {
        SkillLevel[] levels = SkillLevel.values();
        return levels[random.nextInt(levels.length)];
    }
}
