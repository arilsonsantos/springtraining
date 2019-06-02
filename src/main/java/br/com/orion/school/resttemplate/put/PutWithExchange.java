
package br.com.orion.school.resttemplate.put;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.model.Student;
import lombok.extern.log4j.Log4j2;

/**
 * GetObjectTest
 */
@Log4j2
public class PutWithExchange {

    public static void main(String[] args) {
        postObjectStudentTest();
    }

    public static void postObjectStudentTest() {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/orion/api/v1/admin/students").basicAuthentication("maria", "123")
                .build();

        Student student = new Student();
        student.setId(1L);
        student.setName("RINGO");

        ResponseEntity<Student> exchange = restTemplate.exchange("/", HttpMethod.PUT,
                new HttpEntity<>(student, createHeaders()), Student.class);

        log.info(exchange.getStatusCode());
    }

    private static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}