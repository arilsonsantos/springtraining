package br.com.orion.school.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Student
 */
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Student implements Serializable{

    private static final long serialVersionUID = -9219503984838382945L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(min = 5, max=100)
    private String name;

    public Student( String name) {
        this.name = name;
    }

}