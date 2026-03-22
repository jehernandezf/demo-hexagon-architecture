package com.example.hexagon.demo.infrastructure.adapters.out.web.dto;

import java.time.LocalDateTime;

public record EmployeeResponse(
        Long employeeId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDateTime hireDate,
        String jobId,
        Double salary,
        Double commissionPct,
        Long managerId,
        Long departmentId) {
}