package com.proj.pampukh.security.properties;

import com.proj.pampukh.security.SecurityPropertiesConfiguration;
import org.springframework.stereotype.Component;

/**
 * basically component holding security properties
 */
@Component
public class SecurityProperties {

  private final SecurityPropertiesConfiguration.Auth auth;
  private final SecurityPropertiesConfiguration.Jwt jwt;


  public SecurityProperties(SecurityPropertiesConfiguration.Auth auth, SecurityPropertiesConfiguration.Jwt jwt) {
    this.auth = auth;
    this.jwt = jwt;
  }

  public String authHeader() {
    return auth.getHeader();
  }
  public String authPrefix() {
    return auth.getPrefix();
  }
  public String authLoginUri() {
    return auth.getLoginUri();
  }
  public String jwtSecret() {
    return jwt.getSecret();
  }
  public String jwtType() {
    return jwt.getType();
  }
  public String jwtIssuer() {
    return jwt.getIssuer();
  }
  public String jwtAudience() {
    return jwt.getAudience();
  }
  public Long jwtExpirationTime() {
    return jwt.getExpirationTime();
  }
}
