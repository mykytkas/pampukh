package com.proj.pampukh.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * configuration that writes yml security properties into sensible objects
 */
@Configuration
public class SecurityPropertiesConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "security.auth")
  public Auth auth() {
    return new Auth();
  }

  @Bean
  @ConfigurationProperties(prefix = "security.jwt")
  public Jwt jwt() {
    return new Jwt();
  }
  @Getter
  @Setter
  public static class Auth {
    String header;
    String prefix;
    String loginUri;
  }

  @Getter
  @Setter
  public static class Jwt {
    String secret;
    String type;
    String issuer;
    String audience;
    Long expirationTime;
  }


}
