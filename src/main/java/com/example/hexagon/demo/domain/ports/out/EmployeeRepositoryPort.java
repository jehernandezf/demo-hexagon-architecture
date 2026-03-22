package com.example.hexagon.demo.domain.ports.out;

import java.util.Optional;

import com.example.hexagon.demo.domain.model.Employee;

public interface EmployeeRepositoryPort {
    Employee saveEmployee(Employee employee);
    Optional<Employee> findEmployeeById(Long employeeId);
}
