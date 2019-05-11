
package br.com.orion.school.endpoint.student;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.ApplicationStartTests;
import br.com.orion.school.model.Student;

/**
 * GetObjectTest
 */
public class GetForExchange extends ApplicationStartTests {

    //O método abaixo não funciona com paginação

    @Test
    public void getObjectStudentTest() {
        RestTemplate restTemplate = new RestTemplateBuilder()
        .rootUri("http://localhost:8080/orion/api/students").
        basicAuthentication("maria", "123").build();

        ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET,null,
                new ParameterizedTypeReference<List<Student>>() {
                });

                
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }
    
}