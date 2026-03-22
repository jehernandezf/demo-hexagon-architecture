package com.example.hexagon.demo.domain.ports.in;

import java.util.Optional;

import com.example.hexagon.demo.domain.model.Employee;

public interface EmployeeUseCase {
    Employee creatEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Optional<Employee> getEmployeeById(Long employeeId);
}
