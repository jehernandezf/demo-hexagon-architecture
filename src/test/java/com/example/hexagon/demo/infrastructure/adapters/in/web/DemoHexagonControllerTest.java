package com.example.hexagon.demo.infrastructure.adapters.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.hexagon.demo.domain.model.Employee;
import com.example.hexagon.demo.domain.ports.in.EmployeeUseCase;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(DemoHexagonController.class)
class DemoHexagonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeUseCase employeeUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Valida el path /employees con un id de busqueda 1")
    void testGetEmployee() {
        try {
            Employee employeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                    "AD_ASST", 5500d, null, null, 10l);
            when(employeeUseCase.getEmployeeById(1l)).thenReturn(Optional.of(employeeMock));
            mockMvc.perform(get("/demo/hexagon/employees/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.employeeId").value(1l))
                    .andExpect(jsonPath("$.firstName").value("Javier"))
                    .andExpect(jsonPath("$.jobId").value("AD_ASST"));
            verify(employeeUseCase, times(1)).getEmployeeById(anyLong());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSaveEmployee() {
        try {
            Employee employeeMockIn = new Employee(null, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                    "AD_ASST", 5500d, null, null, 10l);

            Employee employeeMockOut = new Employee(1l, "Javier", "Hernandez", "JHDZ", "555-1234", LocalDateTime.now(),
                    "AD_ASST", 5500d, null, null, 10l);

            when(employeeUseCase.creatEmployee(any(Employee.class))).thenReturn(employeeMockOut);
            mockMvc.perform(post("/demo/hexagon/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(employeeMockIn)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.employeeId").value(1l))
                    .andExpect(jsonPath("$.firstName").value("Javier"))
                    .andExpect(jsonPath("$.jobId").value("AD_ASST"))
                    .andExpect(jsonPath("$.email").value("JHDZ"));
            verify(employeeUseCase).creatEmployee(employeeMockIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testUpdateEmployee() {
        try {
            Employee actualEmployeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ", "555-1234",
                    LocalDateTime.now(),
                    "AD_ASST", 5500d, null, null, 10l);

            Employee newEmployeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ@gmail.com", "555-9876",
                    LocalDateTime.now(),
                    "AD_ASST", 5500d, null, null, 10l);

            Employee lastEmployeeMock = new Employee(1l, "Javier", "Hernandez", "JHDZ@gmail.com", "555-9876",
                    LocalDateTime.now(),
                    "AD_ASST", 5500d, null, null, 10l);

            when(employeeUseCase.getEmployeeById(1l)).thenReturn(Optional.of(actualEmployeeMock));
            when(employeeUseCase.updateEmployee(newEmployeeMock)).thenReturn(lastEmployeeMock);
            mockMvc.perform(put("/demo/hexagon/employees/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newEmployeeMock)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.employeeId").value(1l))
                    .andExpect(jsonPath("$.email").value("JHDZ@gmail.com"))
                    .andExpect(jsonPath("$.phoneNumber").value("555-9876"));
            verify(employeeUseCase, times(1)).getEmployeeById(anyLong());
            verify(employeeUseCase).updateEmployee(newEmployeeMock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
