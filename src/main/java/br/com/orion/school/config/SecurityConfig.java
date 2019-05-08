package br.com.orion.school.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.orion.school.service.CustomUserDetailService;

/**
 * SecurityConfig
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").hasRole("ADMIN")// allow h2 console access to admins only
                .anyRequest().authenticated()// all other urls can be access by any authenticated role
                .and().httpBasic().and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF
                //protection to /h2-console
                .and().headers().frameOptions().sameOrigin();//allow use of frame to same
        // origin urls
        //.and().csrf().disable();

    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //     String encodedPassword = passwordEncoder().encode("123");
    //     manager.createUser(User.withUsername("joao").password(encodedPassword).roles("USER").build());
    //     manager.createUser(User.withUsername("maria").password(encodedPassword).roles("USER", "ADMIN").build());
    //     return manager;
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}