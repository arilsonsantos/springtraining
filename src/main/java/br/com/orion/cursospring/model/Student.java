package br.com.orion.cursospring.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Student
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Student {

    private int id;
    private String name;
    public static List<Student> studantList;

    static {
        studentRepository();
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private static void studentRepository() {
        studantList = new ArrayList<>(Arrays.asList(new Student(1, "João"), new Student(2, "Maria")));
    }

}