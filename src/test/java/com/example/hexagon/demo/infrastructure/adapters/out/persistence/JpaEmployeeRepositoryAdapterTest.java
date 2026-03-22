package com.example.hexagon.demo.infrastructure.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.example.hexagon.demo.domain.model.Employee;
import com.example.hexagon.demo.infrastructure.adapters.out.persistence.repository.SpringDataEmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaEmployeeRepositoryAdapterTest {

    @Autowired
    private SpringDataEmployeeRepository springDataEmployeeRepository;
    private JpaEmployeeRepositoryAdapter jpaEmployeeRepositoryAdapter;

    @BeforeEach
    void setUp() {
        jpaEmployeeRepositoryAdapter = new JpaEmployeeRepositoryAdapter(springDataEmployeeRepository);
    }

    @AfterEach
    void cleanAfter() {
        springDataEmployeeRepository.deleteAll();
    }

    @Test
    void testFindEmployeeById() {
        Optional<Employee> employee = jpaEmployeeRepositoryAdapter.findEmployeeById(101l);
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertNotNull(employee.get().getEmployeeId());
        Assertions.assertEquals(101l, employee.get().getEmployeeId());
    }

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee(null, "Javier", "Hernandez", "jehernandezf", "5540928584",
         LocalDateTime.now(), "AD_ASST", 5500.0, null, null, 10l);
        jpaEmployeeRepositoryAdapter.saveEmployee(employee);
        Assertions.assertNotNull(employee.getEmployeeId());
        Assertions.assertEquals("jehernandezf", employee.getEmail());
        Assertions.assertEquals("5540928584", employee.getPhoneNumber());
        Assertions.assertTrue(jpaEmployeeRepositoryAdapter.findEmployeeById(employee.getEmployeeId()).isPresent());
    }
}
