package com.example.hexagon.demo.application.service;

import java.util.Optional;

import com.example.hexagon.demo.domain.model.Employee;
import com.example.hexagon.demo.domain.ports.in.EmployeeUseCase;
import com.example.hexagon.demo.domain.ports.out.EmployeeRepositoryPort;

public class EmployeeService implements EmployeeUseCase {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public EmployeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public Employee creatEmployee(Employee employee) {
        return employeeRepositoryPort.saveEmployee(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepositoryPort.saveEmployee(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepositoryPort.findEmployeeById(employeeId);
    }

}
