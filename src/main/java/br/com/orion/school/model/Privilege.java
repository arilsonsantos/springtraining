package br.com.orion.school.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Privilege
 */
@Entity
@Data
@ToString(exclude = "roles")
@NoArgsConstructor
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String nome;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    
}