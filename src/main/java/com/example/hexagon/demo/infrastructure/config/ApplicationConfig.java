package com.example.hexagon.demo.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hexagon.demo.application.service.EmployeeService;
import com.example.hexagon.demo.domain.ports.out.EmployeeRepositoryPort;

@Configuration
public class ApplicationConfig {

    @Bean
    public EmployeeService employeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        return new EmployeeService(employeeRepositoryPort);
    }
    
}