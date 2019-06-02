package br.com.orion.school.respository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.orion.school.model.Student;
import br.com.orion.school.repository.StudentRepository;

/**
 * StudentRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE) //To use the main database 
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Student student = new Student();
        student.setName("JOAO 50 TESTE");
        studentRepository.save(student);
        assertThat(student.getId()).isNotNull();

    }


    @Test
    public void throwConstraintViolationExceptionNameNull() {
        thrown.expect(ConstraintViolationException.class);

        Student student = createStudent();
        student.setName(null);
        studentRepository.save(student);

    }

    @Test
    public void updateStudent() {
        Student student = createStudent();
        student.setName("Alterado");
        studentRepository.save(student);
        Student studentAlterado = studentRepository.findByNameIgnoreCaseContaining("Alterado").get(0);
        assertThat(studentAlterado.getName()).isEqualTo("Alterado");

    }

    private Student createStudent() {
        Student student = new Student();
        student.setName("JOAO 50 TESTE");
        return student;
    }

    
}