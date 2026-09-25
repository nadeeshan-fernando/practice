# Java Collections Through Employee & Skills REST APIs

This README is updated to match your current Spring Boot model:

- `Employee`
- `EmployeeSkills`
- `EmployeeDummyDataServiceImpl`

It focuses on how Java Collections are used in a more realistic **Employee + Skills** backend example.

---

## Current Entity Design

### `Employee`

- `id : UUID`
- `name : String`
- `department : String`
- `skills : List<EmployeeSkills> = new ArrayList<>()`

### `EmployeeSkills`

- `id : UUID`
- `skillName : String`
- `skillLevel : String`
- `employee : Employee`

### Relationship

- One employee can have many skills
- One skill belongs to one employee
- Mapping used:
    - `@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)`
    - `@ManyToOne(fetch = FetchType.LAZY)`

### Important Helper Methods

```java
public void addSkill(EmployeeSkills skill) {
  skills.add(skill);
  skill.setEmployee(this);
}

public void removeSkill(EmployeeSkills skill) {
  skills.remove(skill);
  skill.setEmployee(null);
}
```

These methods keep **both sides of the relationship in sync**.

---

## Dummy Data Creation Flow

Your dummy data service does the following:

1. Creates `Employee` objects
2. Randomly assigns:
    - employee name
    - department
    - 2 to 5 skills
    - skill level
3. Uses `employee.addSkill(skill)` so each skill is correctly linked back to the employee
4. Deletes old employee data
5. Saves all new employees using `employeeRepository.saveAll(employees)`

Because of `cascade = CascadeType.ALL`, saving employees also saves their skills.

---

# Production-Style API Ideas Based on Your Current Model

## 1. Generate dummy employees

**Endpoint**

```http
POST /api/employees/dummy-data/{count}
```

**Purpose**

Generate random employee data with skills.

**Main Collections Used**

- `List<Employee>`
- `List<EmployeeSkills>`

**Example Response**

```json
{
  "message": "10 dummy employees inserted successfully"
}
```

---

## 2. Get all employees

**Endpoint**

```http
GET /api/employees
```

**Purpose**

Return all employees with their departments.

**Main Collection Used**

- `List<EmployeeResponseDto>`

**Example Response**

```json
{
  "count": 3,
  "data": [
    {
      "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
      "name": "Anton 1",
      "department": "IT"
    },
    {
      "id": "4bbf6b7d-7b6f-4e01-b1a9-22ff91b26002",
      "name": "Nimal 2",
      "department": "HR"
    },
    {
      "id": "7d06f13e-6af2-4d1b-87fb-4a5300f32003",
      "name": "Kamal 3",
      "department": "QA"
    }
  ]
}
```

---

## 3. Get employee by id with skills

**Endpoint**

```http
GET /api/employees/{employeeId}
```

**Purpose**

Return one employee together with all skills.

**Main Collection Used**

- `List<EmployeeSkillDto>` inside the response DTO

**Example Response**

```json
{
  "data": {
    "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
    "name": "Anton 1",
    "department": "IT",
    "skills": [
      {
        "skillName": "JAVA",
        "skillLevel": "BEGINNER"
      },
      {
        "skillName": "SPRING BOOT",
        "skillLevel": "ADVANCED"
      }
    ]
  }
}
```

---

## 4. Get all unique departments

**Endpoint**

```http
GET /api/employees/departments
```

**Purpose**

Return unique departments only.

**Main Collection Used**

- `Set<String>`

**Example Response**

```json
{
  "count": 4,
  "data": [
    "FINANCE",
    "HR",
    "IT",
    "QA"
  ]
}
```

---

## 5. Group employees by department

**Endpoint**

```http
GET /api/employees/grouped-by-department
```

**Purpose**

Group employees based on department.

**Main Collection Used**

- `Map<String, List<EmployeeResponseDto>>`

**Example Response**

```json
{
  "data": {
    "IT": [
      {
        "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
        "name": "Anton 1"
      },
      {
        "id": "a6737f12-4f41-43d8-a7d6-1aa71f77f004",
        "name": "Kasun 4"
      }
    ],
    "HR": [
      {
        "id": "4bbf6b7d-7b6f-4e01-b1a9-22ff91b26002",
        "name": "Nimal 2"
      }
    ]
  }
}
```

---

## 6. Get all unique skills

**Endpoint**

```http
GET /api/employees/skills
```

**Purpose**

Return all available skills without duplicates.

**Main Collection Used**

- `Set<String>`

**Example Response**

```json
{
  "count": 5,
  "data": [
    "ANGULAR",
    "AWS",
    "JAVA",
    "REACT",
    "SPRING BOOT"
  ]
}
```

---

## 7. Get employees by skill name

**Endpoint**

```http
GET /api/employees/by-skill/{skillName}
```

**Example**

```http
GET /api/employees/by-skill/JAVA
```

**Purpose**

Find all employees who have a given skill.

**Main Collection Used**

- `List<EmployeeResponseDto>`

**Example Response**

```json
{
  "count": 2,
  "data": [
    {
      "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
      "name": "Anton 1",
      "department": "IT"
    },
    {
      "id": "7d06f13e-6af2-4d1b-87fb-4a5300f32003",
      "name": "Kamal 3",
      "department": "QA"
    }
  ]
}
```

---

## 8. Group employees by skill level

**Endpoint**

```http
GET /api/employees/grouped-by-skill-level
```

**Purpose**

Return employees grouped by skill level such as BEGINNER, INTERMEDIATE, ADVANCED.

**Main Collection Used**

- `Map<String, List<EmployeeSkillSummaryDto>>`

**Example Response**

```json
{
  "data": {
    "BEGINNER": [
      {
        "employeeName": "Anton 1",
        "skillName": "JAVA"
      }
    ],
    "ADVANCED": [
      {
        "employeeName": "Nimal 2",
        "skillName": "AWS"
      }
    ]
  }
}
```

---

## 9. Get skill count by department

**Endpoint**

```http
GET /api/employees/skill-count-by-department
```

**Purpose**

Return how many skills exist under each department.

**Main Collection Used**

- `Map<String, Long>`

**Example Response**

```json
{
  "data": {
    "IT": 8,
    "HR": 4,
    "QA": 6,
    "FINANCE": 3
  }
}
```

---

## 10. Get employee count by department

**Endpoint**

```http
GET /api/employees/count-by-department
```

**Purpose**

Return employee counts grouped by department.

**Main Collection Used**

- `Map<String, Long>`

**Example Response**

```json
{
  "data": {
    "IT": 5,
    "HR": 2,
    "QA": 3,
    "FINANCE": 1
  }
}
```

---

## 11. Get employee count by skill

**Endpoint**

```http
GET /api/employees/count-by-skill
```

**Purpose**

Return how many employees have each skill.

**Main Collection Used**

- `Map<String, Long>`

**Example Response**

```json
{
  "data": {
    "JAVA": 4,
    "SPRING BOOT": 3,
    "AWS": 2,
    "REACT": 2
  }
}
```

---

## 12. Search employees by multiple filters

**Endpoint**

```http
POST /api/employees/search
```

**Sample Request**

```json
{
  "department": "IT",
  "skillNames": ["JAVA", "SPRING BOOT"],
  "skillLevel": "ADVANCED"
}
```

**Purpose**

Search employees using department, skills, and skill level.

**Main Collections Used**

- Request: `List<String>`
- Response: `List<EmployeeResponseDto>`

**Example Response**

```json
{
  "count": 1,
  "data": [
    {
      "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
      "name": "Anton 1",
      "department": "IT"
    }
  ]
}
```

---

## 13. Get employee filter options

**Endpoint**

```http
GET /api/employees/filter-options
```

**Purpose**

Return dropdown values needed by frontend search screens.

**Main Collections Used**

- `Set<String>` for departments
- `Set<String>` for skill names
- `Set<String>` for skill levels

**Example Response**

```json
{
  "data": {
    "departments": ["FINANCE", "HR", "IT", "QA"],
    "skillNames": ["ANGULAR", "AWS", "JAVA", "PYTHON", "REACT"],
    "skillLevels": ["BEGINNER", "INTERMEDIATE", "ADVANCED"]
  }
}
```

---

## 14. Get department-wise employee summary

**Endpoint**

```http
GET /api/employees/dashboard-summary
```

**Purpose**

Return dashboard-style summary data from employee records.

**Main Collections Used**

- `Map<String, Long>`
- `Map<String, List<String>>`

**Example Response**

```json
{
  "data": {
    "employeeCountByDepartment": {
      "IT": 5,
      "HR": 2,
      "QA": 3
    },
    "topSkillsByDepartment": {
      "IT": ["JAVA", "SPRING BOOT", "AWS"],
      "HR": ["EXCEL", "COMMUNICATION"],
      "QA": ["SQL", "SELENIUM"]
    }
  }
}
```

---

## 15. Remove one skill from an employee

**Endpoint**

```http
DELETE /api/employees/{employeeId}/skills/{skillId}
```

**Purpose**

Remove a skill from an employee.

**Why It Is Important**

Because `orphanRemoval = true` is enabled, removing the skill from the employee's skill list also removes that skill record from the database.

**Main Collection Used**

- `List<EmployeeSkills>`

**Example Response**

```json
{
  "message": "Skill removed successfully"
}
```

---

# Suggested DTOs

You can design DTOs around your current model like this:

- `EmployeeResponseDto`
- `EmployeeDetailsDto`
- `EmployeeSkillDto`
- `EmployeeSkillSummaryDto`
- `EmployeeSearchRequest`
- `EmployeeFilterOptionsDto`
- `DashboardSummaryDto`

---

# Recommended Response Wrapper

To keep the API design clean and consistent, use a common response format.

## Example for list responses

```json
{
  "count": 2,
  "data": [
    {
      "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
      "name": "Anton 1",
      "department": "IT"
    },
    {
      "id": "4bbf6b7d-7b6f-4e01-b1a9-22ff91b26002",
      "name": "Nimal 2",
      "department": "HR"
    }
  ]
}
```

## Example for grouped responses

```json
{
  "data": {
    "IT": [
      {
        "id": "c2d8f1a1-1d46-4db2-b18d-12a1d6b30111",
        "name": "Anton 1"
      }
    ],
    "HR": [
      {
        "id": "4bbf6b7d-7b6f-4e01-b1a9-22ff91b26002",
        "name": "Nimal 2"
      }
    ]
  }
}
```

## Example for map responses

```json
{
  "data": {
    "IT": 5,
    "HR": 2,
    "QA": 3
  }
}
```

---

# Best Practice Notes Based on Your Code

## 1. Initializing the list is correct

```java
private List<EmployeeSkills> skills = new ArrayList<>();
```

This avoids `NullPointerException` when calling:

```java
employee.addSkill(skill);
```

## 2. `addSkill()` is the correct way to maintain the relationship

Instead of doing:

```java
employee.getSkills().add(skill);
```

use:

```java
employee.addSkill(skill);
```

because it also sets:

```java
skill.setEmployee(employee);
```

## 3. Cascade save is important here

Because you are saving employees with:

```java
employeeRepository.saveAll(employees);
```

and `cascade = CascadeType.ALL` is already present, the related `EmployeeSkills` records are saved automatically.

## 4. `orphanRemoval = true` helps during delete/update operations

If a skill is removed from the employee's skill list properly, JPA will remove that orphan record from the database.

## 5. Your dummy data currently stores values in uppercase

```java
skill.setSkillName(randomSkill().toUpperCase());
skill.setSkillLevel(randomSkillLevel().getLevelLabel().toUpperCase());
```

So API responses for skill names and levels will likely come back in uppercase unless you format them in DTO mapping.

---

# Recommended Build Order

If you want to implement step by step, follow this order:

1. `POST /api/employees/dummy-data/{count}`
2. `GET /api/employees`
3. `GET /api/employees/{employeeId}`
4. `GET /api/employees/departments`
5. `GET /api/employees/grouped-by-department`
6. `GET /api/employees/skills`
7. `GET /api/employees/by-skill/{skillName}`
8. `GET /api/employees/count-by-department`
9. `GET /api/employees/count-by-skill`
10. `GET /api/employees/skill-count-by-department`
11. `GET /api/employees/filter-options`
12. `POST /api/employees/search`
13. `GET /api/employees/grouped-by-skill-level`
14. `GET /api/employees/dashboard-summary`
15. `DELETE /api/employees/{employeeId}/skills/{skillId}`

---

# Final Note

This updated version now matches your actual code structure much better than the old generic users/orders/products example.

Your current project mainly demonstrates:

- `List<EmployeeSkills>` inside `Employee`
- `Set<String>` for unique departments/skills
- `Map<String, List<...>>` for grouping
- `Map<String, Long>` for counts and summaries
- `List<Employee>` creation in dummy data generation

