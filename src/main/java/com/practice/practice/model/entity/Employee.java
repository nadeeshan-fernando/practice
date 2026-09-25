package com.practice.practice.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String department;

  @OneToMany(
          mappedBy = "employee",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<EmployeeSkills> skills = new ArrayList<>();

  // keep both sides in sync (important)
  public void addSkill(EmployeeSkills skill) {
    skills.add(skill);
    skill.setEmployee(this);
  }

  public void removeSkill(EmployeeSkills skill) {
    skills.remove(skill);
    skill.setEmployee(null);
  }
}

