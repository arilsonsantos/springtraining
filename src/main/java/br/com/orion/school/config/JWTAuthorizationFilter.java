package br.com.orion.school.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import br.com.orion.school.enumerations.SecurityEnum;
import br.com.orion.school.service.CustomUserDetailService;
import io.jsonwebtoken.Jwts;

/**
 * JWTAuthorizationFilter
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(SecurityEnum.HEADER_STRING);

        if (header == null){ //|| !header.startsWith(SecurityEnum.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getPasswordAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);

    }
    
    private UsernamePasswordAuthenticationToken getPasswordAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(SecurityEnum.HEADER_STRING);
        if (token == null)
            return null;

        String username = Jwts.parser().setSigningKey(SecurityEnum.SECRET)
        .parseClaimsJws(token.replace(SecurityEnum.TOKEN_PREFIX, ""))
                .getBody().getSubject();
        
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        
        return username != null ? new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities()): null;
    }
    
}