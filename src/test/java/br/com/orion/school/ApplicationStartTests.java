package br.com.orion.school;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.orion.school.repository.StudentRepository;

/**
 * ApplicationStartTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class ApplicationStartTests {

    @Autowired
    StudentRepository rep;

    @Test
    public void name() {
        System.out.println("teste" + rep.count());
    }

}