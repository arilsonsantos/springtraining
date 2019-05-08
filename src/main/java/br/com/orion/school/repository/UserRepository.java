package br.com.orion.school.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.orion.school.model.User;

/**
 * UserRepository
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
    
}