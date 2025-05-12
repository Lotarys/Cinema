package ru.romanov.cinema.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.romanov.cinema.entites.User;
import ru.romanov.cinema.services.JWTService;
import ru.romanov.cinema.services.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthFilter(JWTService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtService = jwtService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String login;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(7);
        if (token.equalsIgnoreCase("null") || token.equalsIgnoreCase("undefined")) {
            filterChain.doFilter(request, response);
            return;
        }
        login = jwtService.extractLogin(token);
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userDetailsServiceImpl.loadUserByUsername(login);
            if (jwtService.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
