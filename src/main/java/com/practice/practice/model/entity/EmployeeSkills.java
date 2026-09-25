package com.practice.practice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "employee_skills")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EmployeeSkills {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String skillName;
  private String skillLevel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;
}

