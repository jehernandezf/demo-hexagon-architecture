package com.example.hexagon.demo;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hexagon.demo.infrastructure.adapters.in.web.dto.EmployeeRequest;
import com.example.hexagon.demo.infrastructure.adapters.out.persistence.repository.SpringDataEmployeeRepository;
import com.example.hexagon.demo.infrastructure.adapters.out.web.dto.EmployeeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class DemoHexagonApplicationTest {
        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private SpringDataEmployeeRepository dataEmployeeRepository;

        @BeforeEach
        void cleanBefore() {
                dataEmployeeRepository.deleteAll();
        }

        @AfterEach
        void cleanAfter() {
                dataEmployeeRepository.deleteAll();
        }

        @Test
        @DisplayName("Valida el flujo completo de la aplicación consultando, creando y actualizando empleados")
        void fullEmployeeFlow() {

                // Validamos que se registre la entidad
                EmployeeRequest employeeRequest = new EmployeeRequest("Javier", "Hernandez", "JHDZ", "555-1234",
                                LocalDateTime.now(),
                                "AD_ASST", 5500d, null, null, 10l);
                ResponseEntity<EmployeeResponse> responsePost = restTemplate.postForEntity("/demo/hexagon/employees",
                                employeeRequest, EmployeeResponse.class);
                Assertions.assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
                Assertions.assertNotNull(responsePost.getBody());
                Assertions.assertNotNull(responsePost.getBody().employeeId());

                // Validamos que exista la entidad registrada previamente
                ResponseEntity<EmployeeResponse> responseGet = restTemplate.getForEntity(
                                "/demo/hexagon/employees/" + responsePost.getBody().employeeId(),
                                EmployeeResponse.class);
                Assertions.assertEquals(HttpStatus.OK, responseGet.getStatusCode());
                Assertions.assertNotNull(responseGet.getBody());
                Assertions.assertEquals(responsePost.getBody().employeeId(), responseGet.getBody().employeeId());

                // Validamos que se actualice la entidad registrada previamente
                EmployeeRequest employeeRqstPut = new EmployeeRequest("Javier", "Hernandez", "jehernandezf@email.com",
                                "5559876501",
                                LocalDateTime.now(),
                                "AD_ASST", 5500d, null, null, 10l);

                HttpEntity<EmployeeRequest> httpEntity = new HttpEntity<>(employeeRqstPut);
                ResponseEntity<EmployeeResponse> responseUpdate = restTemplate.exchange(
                                "/demo/hexagon/employees/" + responsePost.getBody().employeeId(),
                                HttpMethod.PUT,
                                httpEntity,
                                EmployeeResponse.class);
                Assertions.assertEquals(HttpStatus.CREATED, responseUpdate.getStatusCode());
                Assertions.assertNotNull(responseUpdate.getBody());
                Assertions.assertNotNull(responseUpdate.getBody().employeeId());
                Assertions.assertEquals("jehernandezf@email.com", responseUpdate.getBody().email());
                Assertions.assertEquals("5559876501", responseUpdate.getBody().phoneNumber());

                // Validamos que retorne un not found cuando se quiera actualizar una entidad
                // inexistente
                ResponseEntity<EmployeeResponse> rpsnUpdate = restTemplate.exchange(
                                "/demo/hexagon/employees/404",
                                HttpMethod.PUT,
                                httpEntity,
                                EmployeeResponse.class);
                Assertions.assertEquals(HttpStatus.NOT_FOUND, rpsnUpdate.getStatusCode());
        }
}