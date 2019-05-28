package br.com.orion.school.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        User user = Optional.ofNullable(userRespository.findByUsernameFetchRole(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> rolesNames = user.getRoles().stream().map(n -> n.getName()).collect(Collectors.toList());
        
        String strRoles = "";

        for (int i = 0; i < rolesNames.size(); ++i, strRoles +=",") {
            strRoles += "ROLE_"+rolesNames.get(i);

        }

        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(strRoles);
  
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);

    }

}