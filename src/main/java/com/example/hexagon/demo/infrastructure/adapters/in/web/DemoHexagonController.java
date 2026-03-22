package com.example.hexagon.demo.infrastructure.adapters.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hexagon.demo.domain.model.Employee;
import com.example.hexagon.demo.domain.ports.in.EmployeeUseCase;
import com.example.hexagon.demo.infrastructure.adapters.in.web.dto.EmployeeRequest;
import com.example.hexagon.demo.infrastructure.adapters.out.web.dto.EmployeeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/demo/hexagon")
@Validated
public class DemoHexagonController {

    private EmployeeUseCase employeeUseCase;

    public DemoHexagonController(EmployeeUseCase employeeUseCase) {
        this.employeeUseCase = employeeUseCase;
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeUseCase.getEmployeeById(employeeId)
                .map(employee -> new ResponseEntity<>(
                        new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                                employee.getEmail(),
                                employee.getPhoneNumber(), employee.getHireDate(),
                                employee.getJobId(), employee.getSalary(),
                                employee.getCommissionPct(),
                                employee.getManagerId(), employee.getDepartmentId()),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(
                        new EmployeeResponse(null, null, null, null, null, null, null, null, null, null, null),
                        HttpStatus.NO_CONTENT));
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> saveEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = new Employee(null, employeeRequest.firstName(), employeeRequest.lastName(),
                employeeRequest.email(),
                employeeRequest.phoneNumber(), employeeRequest.hireDate(), employeeRequest.jobId(),
                employeeRequest.salary(),
                employeeRequest.commissionPct(), employeeRequest.managerId(), employeeRequest.departmentId());

        Employee createdEmployee = employeeUseCase.creatEmployee(employee);
        return new ResponseEntity<>(
                new EmployeeResponse(createdEmployee.getEmployeeId(), createdEmployee.getFirstName(),
                        createdEmployee.getLastName(),
                        createdEmployee.getEmail(), createdEmployee.getPhoneNumber(), createdEmployee.getHireDate(),
                        createdEmployee.getJobId(), createdEmployee.getSalary(), createdEmployee.getCommissionPct(),
                        createdEmployee.getManagerId(), createdEmployee.getDepartmentId()),
                HttpStatus.CREATED);
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable(name = "employeeId") Long employeeId,
            @RequestBody EmployeeRequest employeeRequest) {

        if (employeeUseCase.getEmployeeById(employeeId).isPresent()) {
            Employee employee = new Employee(null, employeeRequest.firstName(), employeeRequest.lastName(),
                    employeeRequest.email(),
                    employeeRequest.phoneNumber(), employeeRequest.hireDate(), employeeRequest.jobId(),
                    employeeRequest.salary(),
                    employeeRequest.commissionPct(), employeeRequest.managerId(), employeeRequest.departmentId());
            employee.setEmployeeId(employeeId);
            employee = employeeUseCase.updateEmployee(employee);

            return new ResponseEntity<>(
                    new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                            employee.getEmail(), employee.getPhoneNumber(),
                            employee.getHireDate(), employee.getJobId(), employee.getSalary(),
                            employee.getCommissionPct(), employee.getManagerId(),
                            employee.getDepartmentId()),
                    HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
