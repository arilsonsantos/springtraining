package br.com.orion.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.orion.school.model.User;
import br.com.orion.school.repository.UserRepository;

/**
 * CustomUserDetailService
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRespository;
    
    @Autowired
    public CustomUserDetailService(UserRepository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRespository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> admins = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        List<GrantedAuthority> users = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.getAdmin() ? admins : users);
    }

}