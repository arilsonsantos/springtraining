package br.com.orion.school.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.orion.school.enumerations.SecurityEnum;
import br.com.orion.school.service.CustomUserDetailService;

/**
 * SecurityConfig
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //      .csrf().disable()
    //         .authorizeRequests()
    //             .antMatchers("/h2-console/**").permitAll()
    //             .antMatchers("/*/students/**").hasRole("USER")
    //             .antMatchers("/*/admin/**").hasRole("ADMIN")
    //             .and()
    //             .httpBasic()
    //             .and().headers().frameOptions().sameOrigin();
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.cors().and().csrf().disable().authorizeRequests()
        //Permite acesso através de outros IPs - sem filtro
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        .and().csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, SecurityEnum.SIGN_UP_URL).permitAll()
        .antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
        .antMatchers("/*/protected/**").hasAnyRole("USER", "ADMINS")
        .antMatchers("/*/admin/**").hasRole("ADMIN")
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //     String encodedPassword = passwordEncoder().encode("123");
    //     manager.createUser(User.withUsername("joao").password(encodedPassword).roles("USER").build());
    //     manager.createUser(User.withUsername("maria").password(encodedPassword).roles("USER", "ADMIN").build());
    //     return manager;
    // }

}