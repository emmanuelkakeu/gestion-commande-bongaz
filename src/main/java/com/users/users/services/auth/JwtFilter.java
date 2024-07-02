package com.users.users.services.auth;

import com.users.users.models.Role;
import com.users.users.models.auth.Jwt;
import com.users.users.models.enums.TypeRole;
import com.users.users.services.UsersServiceImpl;
import com.users.users.services.interfaces.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Service
public class JwtFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UsersServiceImpl usersServiceImpl;

    public JwtFilter(UsersService usersService, JwtService jwtService, UsersServiceImpl usersServiceImpl) {

        this.jwtService = jwtService;
        this.usersServiceImpl = usersServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        log.info("Authorization Header: " + authorization);

        // Permettre l'accès sans authentification aux routes spécifiques
        String path = request.getRequestURI();
        if (path.equals("/gestionUtilisateurs/v1/users/inscription") || path.equals("/gestionUtilisateurs/v1/users/connexion")) {
            log.info("Accès sans authentification pour l'URL : " + path);
            filterChain.doFilter(request, response);
            return;
        }

        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.substring(7).replace("\"", "").trim();
            log.info("Token reçu: " + token);

            try {
                Jwt tokenDansBD = this.jwtService.tokenByValeur(token);
                log.info("Token trouvé dans la base de données: " + tokenDansBD);
                boolean isTokenExpired = jwtService.isTokenExpired(token);
                String username = jwtService.extractUsername(token);
                log.info("Username extrait du token: " + username);

                if (!isTokenExpired && tokenDansBD.getUtilisateurs().getEmail().equals(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = usersServiceImpl.loadUserByUsername(username);
                    log.info("UserDetails trouvé: " + userDetails);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                log.error("Erreur lors de la validation du token JWT: ", e);
            }
        }
        filterChain.doFilter(request, response);
    }


}
