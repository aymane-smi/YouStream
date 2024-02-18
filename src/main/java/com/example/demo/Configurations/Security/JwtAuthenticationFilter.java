package com.example.demo.Configurations.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Services.impl.AdminDetailsImpl;
import com.example.demo.Services.impl.StudentDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final StudentDetailsImpl studentDetailsImpl;
    private final AdminDetailsImpl adminDetailsImpl;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(
        //@Qualifier("StudentDetails")
        StudentDetailsImpl studentDetailsImpl,
        JwtService jwtService,
        //@Qualifier("AdminDetails")
        AdminDetailsImpl adminDetailsImpl
        ){
        this.studentDetailsImpl = studentDetailsImpl;
        this.adminDetailsImpl = adminDetailsImpl;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String header = request.getHeader("Authorization");
        String userType = request.getParameter("userType");
        final String token;
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = header.replace("Bearer ", "");
        jwtService.setToken(token);
        String username = jwtService.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetailsVar = null;
            if(userType.equals("admin"))
                userDetailsVar = studentDetailsImpl.loadUserByUsername(username);
            else if(userType.equals("student"))
                userDetailsVar = adminDetailsImpl.loadUserByUsername(username);
            
            if (jwtService.validateToken(token, userDetailsVar)) {
                UsernamePasswordAuthenticationToken authenticationToken = null;
                if(userType.equals("admin"))
                    authenticationToken = new UsernamePasswordAuthenticationToken(
                        adminDetailsImpl, null, userDetailsVar.getAuthorities()
                    );
                else if(userType.equals("student"))
                    authenticationToken = new UsernamePasswordAuthenticationToken(
                        studentDetailsImpl, null, userDetailsVar.getAuthorities()
                    );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
