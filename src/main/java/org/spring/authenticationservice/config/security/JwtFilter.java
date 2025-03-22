package org.spring.authenticationservice.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.authenticationservice.service.JwtService;
import org.spring.authenticationservice.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    // Comprehensive list of public paths
    private static final List<String> PUBLIC_PATHS = List.of(
            // Authentication and registration endpoints
            "/login",
            "/register",
            "/drug-importer/register",
            "/validate",
            "/activate",

            // Test endpoints
            "/api/test/hello",
            "/test/hello",
            "/api/v1/test/hello",

            // Swagger and OpenAPI paths
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",

            // Additional common public paths
            "/actuator/**",
            "/error"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Debug logging to help diagnose path matching issues
        logger.debug("Incoming request path: {}", path);

        // Check if the current path is a public path
        boolean isPublicPath = isPublicPath(path);

        if (isPublicPath) {
            // Skip authentication for public paths
            logger.debug("Public path detected, skipping authentication: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        // Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // If no Authorization header or it doesn't start with "Bearer ", proceed without authentication
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No valid Authorization header, proceeding without authentication");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token and username
        try {
            // Get the token (remove "Bearer " prefix)
            String token = authHeader.substring(7);

            // Extract username from token
            String username = jwtService.getClaimsFromToken(token).getSubject();

            // If username is extracted and no authentication exists yet
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details
                UserDetailServiceImpl userDetailsService = applicationContext.getBean(UserDetailServiceImpl.class);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate token
                if (jwtService.Validate(token, userDetails)) {
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    // Set details
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Log exception
            logger.error("Cannot set user authentication", e);
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Enhanced path matching method to handle various URL patterns
     * @param path The request path
     * @return true if the path is considered public
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(publicPath ->
                // Exact match
                path.equals(publicPath) ||
                        // Starts with
                        path.startsWith(publicPath) ||
                        // Wildcard match for paths with /**
                        (publicPath.endsWith("/**") && path.startsWith(publicPath.substring(0, publicPath.length() - 3)))
        );
    }
}