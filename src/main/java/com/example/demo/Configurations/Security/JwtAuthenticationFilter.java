package com.example.demo.Configurations.Security;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Exceptions.UnAuthorizedException;
import com.example.demo.Services.impl.AdminDetailsImpl;
import com.example.demo.Services.impl.StudentDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final StudentDetailsImpl studentDetailsImpl;
    private final AdminDetailsImpl adminDetailsImpl;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(
        StudentDetailsImpl studentDetailsImpl,
        JwtService jwtService,
        AdminDetailsImpl adminDetailsImpl
        ){
        this.studentDetailsImpl = studentDetailsImpl;
        this.adminDetailsImpl = adminDetailsImpl;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        try{
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
            if(username.isEmpty())
                throw new UnAuthorizedException("unauthorized");
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetailsVar = null;
                if(userType.equals("student"))
                    userDetailsVar = studentDetailsImpl.loadUserByUsername(username);
                else if(userType.equals("admin"))
                    userDetailsVar = adminDetailsImpl.loadUserByUsername(username);
                if(userDetailsVar == null)
                    throw new UnAuthorizedException("userDetails is null");
                if (jwtService.validateToken(token, userDetailsVar)) {
                    UsernamePasswordAuthenticationToken authenticationToken = null;
                    authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetailsVar, null, userDetailsVar.getAuthorities()
                        );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    throw new UnAuthorizedException("unathorized");
                }
            }
            filterChain.doFilter(request, response);
        }catch(UnAuthorizedException ex){
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{'error': 'unauthorized'}");

        }catch(ExpiredJwtException ex){
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{'error': 'expired'}");

        }
    }
}
