package com.example.hexagon.demo.infrastructure.adapters.out.persistence;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.hexagon.demo.domain.model.Employee;
import com.example.hexagon.demo.domain.ports.out.EmployeeRepositoryPort;
import com.example.hexagon.demo.infrastructure.adapters.out.persistence.entity.EmployeeEntity;
import com.example.hexagon.demo.infrastructure.adapters.out.persistence.repository.SpringDataEmployeeRepository;

@Component
public class JpaEmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final SpringDataEmployeeRepository springDataEmployeeRepository;

    public JpaEmployeeRepositoryAdapter(SpringDataEmployeeRepository springDataEmployeeRepository) {
        this.springDataEmployeeRepository = springDataEmployeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new ModelMapper().map(employee, EmployeeEntity.class);
        springDataEmployeeRepository.save(employeeEntity);
        employee.setEmployeeId(employeeEntity.getEmployeeId());
        return employee;
    }

    @Override
    public Optional<Employee> findEmployeeById(Long employeeId) {
        return springDataEmployeeRepository.findById(employeeId)
                .map(employee -> new ModelMapper().map(employee, Employee.class));
    }
}
