
package br.com.orion.school.endpoint.student.get;

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
import lombok.extern.log4j.Log4j2;

/**
 * GetObjectTest
 */
@Log4j2
public class GetForExchange extends ApplicationStartTests {
    
    
    @Test
    public void getObjectStudentTest() {

        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/orion/api/v1/admin/students/pages")
                .basicAuthentication("maria", "123").build();

        ResponseEntity<PageableResponseWrapper<Student>> exchange = restTemplate.exchange("/?sort=name,desc&sort=id,asc", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponseWrapper<Student>>() {
                });

        log.info(exchange.getBody());

        exchange.getBody().getContent().forEach(f -> System.out.println(f.getId() + " - " + f.getName()));
        extracted(exchange);
    }

    private void extracted(ResponseEntity<PageableResponseWrapper<Student>> exchange) {
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

}