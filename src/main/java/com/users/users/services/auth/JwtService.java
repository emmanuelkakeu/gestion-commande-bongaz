package com.users.users.services.auth;

import com.users.users.models.Utilisateurs;
import com.users.users.models.auth.Jwt;
import com.users.users.repository.JwtRepository;
import com.users.users.services.UsersServiceImpl;
import com.users.users.services.interfaces.UsersService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    public static final String TOKEN_INVALID = "Le jeton de rafraîchissement est invalide ou a expiré.";
    private final JwtRepository jwtRepository;
    private final UsersServiceImpl usersServiceImpl;

    public JwtService(JwtRepository jwtRepository, UsersServiceImpl usersServiceImpl) {
        this.jwtRepository = jwtRepository;
        this.usersServiceImpl = usersServiceImpl;
    }


    @Transactional(readOnly = true)
    public Jwt tokenByValeur(String valeur) {
        log.info("Recherche du token dans la base de données: " + valeur);
        Jwt jwt = this.jwtRepository.findByValeurAndDesactiverAndExpired(
                valeur,
                false,
                false
        ).orElseThrow(() -> {
            log.error("Token invalide ou inconnu: " + valeur);
            return new RuntimeException("Token invalide ou inconnu");
        });
        // Initialisation de la collection utilisateurs pour éviter LazyInitializationException
        Hibernate.initialize(jwt.getUtilisateurs());
        log.info("Token trouvé et utilisateurs initialisés: " + jwt);
        return jwt;
    }





    public Map<String, String> generate(String username) {
        Utilisateurs utilisateurs = this.usersServiceImpl.loadUserByUsername(username);

        this.disableTokens(utilisateurs);
        final Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(utilisateurs));

        final Jwt jwt = Jwt
                .builder()
                .valeur(jwtMap.get(BEARER))
                .desactiver(false)
                .expired(false)
                .utilisateurs(utilisateurs)
                .build();

        this.jwtRepository.save(jwt);
        return jwtMap;
    }

    private Map<String, String> generateJwt(Utilisateurs utilisateurs) {
        final Instant currentTime = Instant.now();
        final Instant expirationTime = currentTime.plusSeconds( 2 * 3600);  // 3600 secondes = 1 heure
        final Map<String, Object> claims = Map.of(
                "nom", utilisateurs.getName(),
                Claims.EXPIRATION, Date.from(expirationTime),
                Claims.SUBJECT, utilisateurs.getEmail()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(Date.from(currentTime))
                .setExpiration(Date.from(expirationTime))
                .setSubject(utilisateurs.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey(){
        String ENCRYPTION_KEY = "5b3fc907ae092dea6a880595c87e81c2519e1496b9053dd05d145bf7d531e1c9";
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public String extractUsername(String token) {
        return  this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }



    private <T> T getClaim(String token, Function<Claims, T> function) {

        Claims claims = getAllClaims(token);
        return  function.apply(claims);
    }

    private Claims getAllClaims(String token) {

        return  Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {

//        if(jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
//            throw new RuntimeException(TOKEN_INVALID);
//        }
//        this.disableTokens(jwt.getUtilisateurs());
//        return this.generate(jwt.getUtilisateurs().getEmail());
//    }
    private void disableTokens(Utilisateurs utilisateurs) {
        final List<Jwt> jwtList = this.jwtRepository.findUtilisateurs(utilisateurs.getEmail()).peek(
                jwt -> {
                    jwt.setExpired(true);
                    jwt.setDesactiver(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }

//    public void deconnexion() {
//        Utilisateurs utilisateurs = (Utilisateurs) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Jwt jwt =  this.jwtRepository.findUtilisateurValidToken(utilisateurs.getEmail(),
//                false,
//                false).orElseThrow(() -> new RuntimeException("TOKEN_VALIDE"));
//        jwt.setExpired(true);
//        jwt.setDesactiver(true);
//
//        this.jwtRepository.save(jwt);
//    }
@Scheduled(cron = "0 0/15 * * * *")
public void removeUselessJwt() {
    log.info("Suppression des tokens à {}", Instant.now());
    this.jwtRepository.deleteAllByExpiredAndDesactiver(true, true);
}
}
