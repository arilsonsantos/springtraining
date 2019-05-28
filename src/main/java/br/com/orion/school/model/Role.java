package br.com.orion.school.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Roles
 */
@Entity
@Data
@ToString(of = "name")
@EqualsAndHashCode(exclude = { "users", "privileges" })
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;


    @ManyToMany
    @JoinTable(name = "role_privilege", 
    joinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "id_privilege", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    
}