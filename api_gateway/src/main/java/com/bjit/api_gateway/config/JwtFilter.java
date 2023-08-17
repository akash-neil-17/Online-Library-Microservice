package com.bjit.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtFilter extends GenericFilterBean {

    private static final String SECRET_KEY = "7134743677397A24432646294A404E635266556A586E32723575387821412544";
    private static final String USER_URL_REGEX = "^/book-service/book/buy$";
    private static final String ADMIN_URL_REGEX = "^/book-service/(create|update|delete)$";
    private static final String USER_ADMIN_URL_REGEX = "^/book-service/book/(all|\\d+)$";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Invalid or missing Authorization header");
        }

        final String token = authHeader.substring(7);
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build();
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        // getting the roles as List<String> and converting it to string
        String role = (String) claims.get("role", List.class).get(0);

        // extracting the URL
        String requestURI = request.getRequestURI();

        // checking the validity of the URL
        if (!matchUrl(requestURI)) {
            throw new ServletException("Invalid URL_404 NOT FOUND");
        }

        // checking if the user has required ROLE to access that URL
        if (role.equals("ADMIN")) {
            boolean matchedAdmin = requestURI.matches(ADMIN_URL_REGEX);
            boolean matchedUserAdmin = requestURI.matches(USER_ADMIN_URL_REGEX);
            // if an ADMIN is trying to access USER only URL, then throw exception
            if (!matchedAdmin && !matchedUserAdmin) {
                throw new ServletException("You are not authorized to access the url");
            }
            // if a USER is trying to access ADMIN only URL, then throw exception
        } else if (role.equals("USER")) {
            boolean matchedUser = requestURI.matches(USER_URL_REGEX);
            boolean matchedUserAdmin = requestURI.matches(USER_ADMIN_URL_REGEX);
            if ((!matchedUser && !matchedUserAdmin)) {
                throw new ServletException("You are not authorized to access the url");
            }
        }

        // passing the request and response to the next filter of the chain
        filterChain.doFilter(request, response);
    }

    private boolean matchUrl(String url) {
        boolean matchedAdmin = url.matches(ADMIN_URL_REGEX);
        boolean matchedUser = url.matches(USER_URL_REGEX);
        boolean matchedUserAdmin = url.matches(USER_ADMIN_URL_REGEX);
        return matchedAdmin || matchedUser || matchedUserAdmin;
    }
}
