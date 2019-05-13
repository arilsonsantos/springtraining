
package br.com.orion.school.endpoint.student;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.ApplicationStartTests;
import br.com.orion.school.model.Student;
import br.com.orion.school.model.wrapper.PageableResponseWrapper;

/**
 * GetObjectTest
 */
public class GetForExchange extends ApplicationStartTests {

    @Test
    public void getObjectStudentTest() {
        RestTemplate restTemplate = new RestTemplateBuilder()
        .rootUri("http://localhost:8080/orion/api/students").
        basicAuthentication("maria", "123").build();

        ResponseEntity<PageableResponseWrapper<Student>> exchange = restTemplate
            .exchange("/?sort=name,desc&sort=id,asc", HttpMethod.GET,null,  new ParameterizedTypeReference<PageableResponseWrapper<Student>>() {
                });
                
        extracted(exchange);
    }

    private void extracted(ResponseEntity<PageableResponseWrapper<Student>> exchange) {
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }
    
}