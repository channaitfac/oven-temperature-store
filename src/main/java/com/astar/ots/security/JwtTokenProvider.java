package com.astar.ots.security;

import com.astar.ots.exception.AuthenticationException;
import com.astar.ots.util.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.astar.ots.util.Constants.*;
import static com.astar.ots.util.Constants.ErrorMessages.EXPIRED_JWT_TOKEN;

@Component
public class JwtTokenProvider {

  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:1800000}") // 30 minutes
  private long validityInMilliseconds = 1800000;

  @Autowired
  private JwtUserDetails jwtUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<Role> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put(AUTH, roles.stream()
            .map(s -> new SimpleGrantedAuthority(s.getAuthority()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = jwtUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(BEARER)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new AuthenticationException(EXPIRED_JWT_TOKEN);
    }
  }
}
