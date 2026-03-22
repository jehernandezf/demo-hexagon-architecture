package com.example.hexagon.demo.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.hexagon.demo.domain.model.Employee;
import com.example.hexagon.demo.domain.ports.out.EmployeeRepositoryPort;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepositoryPort employeeRepositoryPortMock;

    @BeforeEach
    void setUp() {
        employeeRepositoryPortMock = Mockito.mock(EmployeeRepositoryPort.class);
        employeeService = new EmployeeService(employeeRepositoryPortMock);
    }

    @Test
    void testGetEmployeeById() {
        Employee employeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                "AD_ASST", 5500d, null, null, 10l);

        when(employeeRepositoryPortMock.findEmployeeById(1l)).thenReturn(Optional.of(employeeMock));

        Optional<Employee> empleado = employeeService.getEmployeeById(1l);
        Assertions.assertTrue(empleado.isPresent());
        Assertions.assertEquals("Javier", empleado.get().getFirstName());
        Assertions.assertEquals("Hernandez", empleado.get().getLastName());
        Assertions.assertEquals("AD_ASST", empleado.get().getJobId());
        verify(employeeRepositoryPortMock, times(1)).findEmployeeById(1l);
    }

    @Test
    void testCreatEmployee() {
        Employee employeeMockIn = new Employee(null, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                "AD_ASST", 5500d, null, null, 10l);

        Employee employeeMockOut = new Employee(1l, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                "AD_ASST", 5500d, null, null, 10l);

        when(employeeRepositoryPortMock.saveEmployee(any(Employee.class))).thenReturn(employeeMockOut);
        Employee result = employeeService.creatEmployee(employeeMockIn);

        Assertions.assertNotNull(result.getEmployeeId());
        Assertions.assertEquals(1l, result.getEmployeeId());
        verify(employeeRepositoryPortMock).saveEmployee(employeeMockIn);
    }

    @Test
    void testUpdateEmployee() {
        Employee actualEmployeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                "AD_ASST", 5500d, null, null, 10l);
        
        Employee newEmployeeMock = new Employee(null, "Javier", "Hernandez", "JHDZ@gmail.com", "555-9876",
                LocalDateTime.now(),
                "AD_ASST", 5500d, null, null, 10l);
        
        Employee lastEmployeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ@gmail.com", "555-9876",
                LocalDateTime.now(),
                "AD_ASST", 5500d, null, null, 10l);

        when(employeeRepositoryPortMock.findEmployeeById(1l)).thenReturn(Optional.of(actualEmployeeMock));
        Optional<Employee> actualEmployee = employeeService.getEmployeeById(1L);
        Assertions.assertTrue(actualEmployee.isPresent());
        newEmployeeMock.setEmployeeId(1l);
        verify(employeeRepositoryPortMock, times(1)).findEmployeeById(1l);
        
        when(employeeRepositoryPortMock.saveEmployee(newEmployeeMock)).thenReturn(lastEmployeeMock);
        Employee result = employeeService.updateEmployee(newEmployeeMock);
        Assertions.assertNotNull(result.getEmployeeId());
        Assertions.assertEquals(1l, result.getEmployeeId());
        Assertions.assertEquals("JHDZ@gmail.com", result.getEmail());
        Assertions.assertEquals("555-9876", result.getPhoneNumber());
        verify(employeeRepositoryPortMock).saveEmployee(newEmployeeMock);
    }
}