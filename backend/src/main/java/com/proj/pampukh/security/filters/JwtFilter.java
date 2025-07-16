package com.proj.pampukh.security.filters;

import com.proj.pampukh.security.JwtUtil;
import com.proj.pampukh.security.properties.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final SecurityProperties securityProperties;


  public JwtFilter(JwtUtil jwtUtil, SecurityProperties securityProperties) {
    this.jwtUtil = jwtUtil;
    this.securityProperties = securityProperties;
  }

  private final RequestMatcher[] ignorePaths = new RequestMatcher[]{
      PathPatternRequestMatcher.withDefaults().matcher("/pampukh/register"),
      PathPatternRequestMatcher.withDefaults().matcher("/pampukh/login"),
      PathPatternRequestMatcher.withDefaults().matcher("/h2-console/**"),
  };

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    System.out.println("ignoring!");
    for (RequestMatcher requestMatcher : ignorePaths) {
      if (requestMatcher.matches(request)) {
        System.out.println("really ignoring!!");
        return true;
      }
    }
    return false;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {
    // TODO: expand with exceptions
    try {

      String token = extractToken(request);

      if (token == null) {
        throw new RuntimeException("aahhh");
      }

      if (!jwtUtil.validateJwtToken(token)) {
        throw new RuntimeException("ahh");
      }

      String username = jwtUtil.getUsernameFromJwtToken(token);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);

      System.out.println("passed custom filter");
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("ти довбойоб");
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String token = request.getHeader(securityProperties.authHeader());

    if (!token.isBlank() && token.startsWith(securityProperties.authPrefix())) {

      return token;
    }
    return null;
  }
}
