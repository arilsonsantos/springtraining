package br.com.orion.school.endpoint;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class StudentEndpointTokenTest {

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

    private HttpEntity<Void> protectedHeader;
    private HttpEntity<Void> admindHeader;
    private HttpEntity<Void> invalidHeader;


   
    @Before
    public void configProtectedHeaders() {
        String str = "{ \"username\": \"joao\", \"password\": \"123\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
        this.protectedHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configAdminHeaders() {
        String str = "{ \"username\": \"maria\", \"password\": \"123\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login", str, String.class).getHeaders();
        this.admindHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configInvalidHeaders() {
        String str = "{ \"username\": \"1\", \"password\": \"1\"}";
        HttpHeaders headers = restTemplate.postForEntity("/login/", str, String.class).getHeaders();
        this.invalidHeader = new HttpEntity<>(headers);
    }

    
    @Test
    public void listStudentWithTokenIncorrectReturnStatusCode401() {
        ResponseEntity<String> student = restTemplate.exchange("/v1/protected/students", GET, invalidHeader, String.class);
        Assertions.assertThat(student.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void listStudentWithValideTokenReturnStatusCode200() {
        ResponseEntity<String> student = restTemplate.exchange("/v1/protected/students", GET, protectedHeader, String.class);
        Assertions.assertThat(student.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void listStudentWithUserAndPasswordCorrectReturnStatusCode200UsingMock() {
        ResponseEntity<String> student = restTemplate.exchange("/v1/protected/students", GET, protectedHeader, String.class);
        Assertions.assertThat(student.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void getStudentWithUserAndPasswordCorrectReturnStatusCode200UsingMock() {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        ResponseEntity<Student> response = restTemplate.exchange("/v1/protected/students/{id}", GET, protectedHeader, Student.class,
                student.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStudentWithUserAndPasswordCorrectReturnStatusCode404UsingMock() {
        ResponseEntity<Student> response = restTemplate.exchange("/v1/protected/students/{id}", GET, protectedHeader, Student.class, -1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    

    @Test
    public void deleteStudentWithUserAndPasswordCorrectReturnStatusCode200UsingMock() throws RestClientException {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        ResponseEntity<String> response = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE, admindHeader,
                String.class, student.getId());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteStudentWithUserAndPasswordCorrectReturnStatusCode404UsingMock() throws RestClientException {
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        ResponseEntity<String> response = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE, admindHeader,
                String.class, -1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndStudentDoesNotExistShouldReturnStatusCode404() throws Exception {
        String token = admindHeader.getHeaders().get("Authorization").get(0);
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/students/{id}", -1L).header("Authorization", token))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteWhenUserHasNoRoleAdminAndStudentExistShouldReturnStatusCode403() throws Exception {
        String token = admindHeader.getHeaders().get("Authorization").get(0);
        Student student = new Student(1L, "joao");
        BDDMockito.when(studentService.getById(student.getId())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/students/{id}", student.getId())
            .header("Autorization", token))
            .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void createShouldReturnStatusCode201WithMockMVC() throws Exception {
        String token = admindHeader.getHeaders().get("Authorization").get(0);
        Student student = new Student(5L, "João Novo");
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);
        String json = objectMapper.writeValueAsString(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/admin/students/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
            .header("Authorization", token))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void createShouldReturnStatusCode201() throws Exception {
        Student student = new Student(5L, "João Novo");
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);

        ResponseEntity<String> response = restTemplate.exchange("/v1/admin/students/", POST, new HttpEntity<>(student, admindHeader.getHeaders()), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void createWhenNameIsNullShouldReturnStatusCode400() throws Exception {
        Student student = new Student(5L, null);
        BDDMockito.when(studentRepository.save(student)).thenReturn(student);

        ResponseEntity<String> response = restTemplate.exchange("/v1/admin/students/", POST, new HttpEntity<>(student, admindHeader.getHeaders()), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    

}