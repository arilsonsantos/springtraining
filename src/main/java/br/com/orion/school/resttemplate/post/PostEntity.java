
package br.com.orion.school.resttemplate.post;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.orion.school.model.Student;
import lombok.extern.log4j.Log4j2;

/**
 * GetObjectTest
 */
@Log4j2
public class PostEntity {

    public static void main(String[] args) {
        postObjectStudentTest();
    }

    public static void postObjectStudentTest() {

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:8080/orion/api/v1/admin/students").basicAuthentication("maria", "123")
                .build();

        Student student = new Student();
        student.setName("RINGO");

        ResponseEntity<Student> entity = restTemplate.postForEntity("http://localhost:8080/orion/api/v1/admin/students",
                student, Student.class);

        log.info(entity.getStatusCode());
    }

}