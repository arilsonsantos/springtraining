package br.com.orion.school.endpoint;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestClientException;

import br.com.orion.school.model.Student;
import br.com.orion.school.repository.StudentRepository;
import br.com.orion.school.service.StudentService;

/**
 * StudentEndpointTest
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;


    @TestConfiguration
    static class InnerStudentEndpointTest {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("maria", "123");
        }

    }
    
    @Test
    public void listStudentWithUserAndPasswordIncorrectReturnStatusCode401() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> student = restTemplate.getForEntity("/v1/protected/students", String.class);
        Assertions.assertThat(student.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void listStudentWithUserAndPasswordCorrectReturnStatusCode200() {
        ResponseEntity<String> student = restTemplate.getForEntity("/v1/protected/students", String.class);
        Assertions.assertThat(student.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void listStudentWithUserAndPasswordCorrectReturnStatusCode200UsingMock() {
        List<Student> students = Arrays.asList(new Student(1L, "joao"), new Student(2L, "maria"));
        BDDMockito.when(studentRepository.findAll()).thenReturn(students);
        ResponseEntity<String> student = restTemplate.getForEntity("/v1/protected/students", String.class);
        Assertions.assertThat(student.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void getStudentWithUserAndPasswordCorrectReturnStatusCode200UsingMock() {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        ResponseEntity<Student> response = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class,
                student.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStudentWithUserAndPasswordCorrectReturnStatusCode404UsingMock() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, -1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    

    @Test
    public void deleteStudentWithUserAndPasswordCorrectReturnStatusCode200UsingMock() throws RestClientException {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        ResponseEntity<String> response = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE, null,
                String.class, student.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteStudentWithUserAndPasswordCorrectReturnStatusCode404UsingMock() throws RestClientException {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        ResponseEntity<String> response = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE, null,
                String.class, -1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @WithMockUser(username = "maria", password = "123", roles = {"USER", "ADMIN"})
    public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistShouldReturnStatusCode404() throws Exception {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/students/{id}", -1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "maria", password = "123", roles = {"USER"})
    public void deleteWhenUserHasNoRoleAdminAndStudentExistShouldReturnStatusCode403() throws Exception {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/students/{id}", student.getId()))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "maria", password = "123", roles = {"USER", "ADMIN"})
    public void createShouldReturnStatusCode201WithMockMVC() throws Exception {
        Student student = new Student(5L, "João Novo");
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);
        String json = objectMapper.writeValueAsString(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/admin/students/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void createShouldReturnStatusCode201() throws Exception {
        Student student = new Student(5L, "João Novo");
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/admin/students/", student, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void createWhenNameIsNullShouldReturnStatusCode400() throws Exception {
        Student student = new Student(5L, null);
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);

        ResponseEntity<String> response = restTemplate.postForEntity("/v1/admin/students/", student, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    

}