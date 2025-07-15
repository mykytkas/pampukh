package com.proj.pampukh.security;

import com.proj.pampukh.security.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

  private final SecurityProperties securityProperties;


  public JwtUtil(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }

  /**
   * generate jwt token from username
   *
   * @param username - used as subject to generate the token
   * @return generated jwt token
   */
  public String generateAuthToken(String username) {
    // prepare key in appropriate object
    byte[] signKey = securityProperties.jwtSecret().getBytes();
    SecretKey secretKey = Keys.hmacShaKeyFor(signKey);

    long time = new Date().getTime();
    String token = Jwts.builder()
        .header().add("type", securityProperties.jwtType()).and()
        .issuer(securityProperties.jwtIssuer())
        .audience().add(securityProperties.jwtAudience()).and()
        .expiration(new Date(time + securityProperties.jwtExpirationTime()))
        .subject(username)
        .claim("token_created", time)
        .signWith(secretKey, Jwts.SIG.HS512)
        .compact();

    return securityProperties.authPrefix().concat(token);
  }

  public String generateAuthToken(Authentication auth) {
    AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
    return generateAuthToken(principal.getUsername());
  }

  public boolean validateJwtToken(String jwtToken) {
    // remove prefix
    String stripped = jwtToken.substring(securityProperties.authPrefix().length());

    try {
      byte[] signKey = securityProperties.jwtSecret().getBytes();
      SecretKey secretKey = Keys.hmacShaKeyFor(signKey);

      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(stripped);

      return true;
    } catch (Exception e) { // TODO: expand
      System.err.println(e.getMessage());
    }
    return false;
  }

  public String getUsernameFromJwtToken(String jwtToken) {
    // remove prefix
    String stripped = jwtToken.substring(securityProperties.authPrefix().length());

    try {
      byte[] signKey = securityProperties.jwtSecret().getBytes();
      SecretKey secretKey = Keys.hmacShaKeyFor(signKey);

      Claims payload = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(stripped)
          .getPayload();

      return payload.getSubject();

    } catch (Exception e) { // TODO: expand
      System.err.println(e.getMessage());
    }
    return null;
  }
}
