package com.example.hexagon.demo.infrastructure.adapters.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hexagon.demo.infrastructure.adapters.out.persistence.entity.EmployeeEntity;

public interface SpringDataEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
