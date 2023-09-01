package com.example.academia.config;

import com.example.academia.repository.UserRepository;
import com.example.academia.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.academia.model.User;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    final TokenService tokenService;
    final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var login = tokenService.validateToken(token);
                UserDetails user = userRepository.findByEmail(login);

                if (user != null && user instanceof User) {
                    User userDetails = (User) user;                    
                    Long userId = userDetails.getId();
                    //verifica se o ID do user no token corresponde ao ID associado ao recurso
                    Long resourceId = extractResourceIdFromRequest(request);
                    if (resourceId != null && userId.equals(resourceId)) {
                        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        //se o id do user no token nao corresponde ao ID do recurso, negua o acesso
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            System.out.println("Erro de autenticação: " + ex.getMessage());
        }
    }

    private Long extractResourceIdFromRequest(HttpServletRequest request) {
        //obtem a parte final da URL do request, que contém o ID.
        String requestURI = request.getRequestURI();

        //dividi a URL para pegar o conteudo depois das "/" e obtem o ultimo segmento        
        String[] segments = requestURI.split("/");
        if (segments.length > 0) {
            String lastSegment = segments[segments.length - 1];
            try {
                //tenta converter o ultimo segmento em um Long (ID)
                return Long.parseLong(lastSegment);
            } catch (NumberFormatException e) {
                //se nao  conseguir converter em Long, retorna null
                return null;
            }
        }
        //se nao conseguir extrair o ID da URL, retorna null
        return null;
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
