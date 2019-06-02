package br.com.orion.school.resttemplate.get;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.model.Student;

/**
 * GetObjectTest
 */
public class GetObjectTest {
    
    public static void main(String[] args) {
        getObjectStudentTest();
    }

    public static void getObjectStudentTest() {
        RestTemplate restTemplate = new RestTemplateBuilder()
        .rootUri("http://localhost:8080/orion/api/v1/protected/students").
        basicAuthentication("maria", "123").build();

        Student student = restTemplate.getForObject("/{id}", Student.class, "1");

        System.out.println(student.getName());
    }
    
}