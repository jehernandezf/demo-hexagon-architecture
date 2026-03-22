package com.example.hexagon.demo.infrastructure.adapters.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMPLOYEES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Long employeeId;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    
    @Column(name = "HIRE_DATE", nullable = false)
    private LocalDateTime hireDate;
    
    @Column(name = "JOB_ID", nullable = false)
    private String jobId;
    
    @Column(name = "SALARY")
    private Double salary;
    
    @Column(name = "COMMISSION_PCT")
    private Double commissionPct;
    
    @Column(name = "MANAGER_ID")
    private Long managerId;
    
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;
}
