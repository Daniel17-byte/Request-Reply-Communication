package org.sd.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sd.gateway.authentication.JwtUtil;
import org.sd.gateway.model.AuthRequest;
import org.sd.gateway.model.AuthResponse;
import org.sd.gateway.model.User;
import org.sd.gateway.service.CustomUserDetailsService;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthController {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final CustomUserDetailsService customUserDetailsService;
    private static final String USERS_SERVICE_URL = "http://users:8080/api/users";

    public AuthController(JwtUtil jwtUtil, RestTemplate restTemplate, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthRequest authRequest) {
        try {
            String usersServiceUrl = USERS_SERVICE_URL + "/authenticate";

            HttpHeaders headers = getHttpHeaders(request);

            HttpEntity<AuthRequest> entity = new HttpEntity<>(authRequest, headers);

            ResponseEntity<User> userResponse = restTemplate.exchange(usersServiceUrl, HttpMethod.POST, entity, User.class);

            if (userResponse.getStatusCode() == HttpStatus.OK && userResponse.getBody() != null) {
                User user = userResponse.getBody();

                String jwt = jwtUtil.generateToken(user);

                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

                response.setHeader("Authorization", "Bearer " + jwt);
                return ResponseEntity.ok(new AuthResponse(jwt));
            } else {
                return ResponseEntity.status(userResponse.getStatusCode()).body("Authentication failed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/getLoggedInUser")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token missing or invalid");
        }

        String jwt = authorizationHeader.substring(7);
        String username;
        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        if (username == null || jwtUtil.isTokenExpired(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token expired or invalid");
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null && user.getUsername().equals(username)) {
                System.out.println(user.getUsername());
                return ResponseEntity.ok(user);
            }
        }

        User userDetails;

        try {
            userDetails = customUserDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UserDetails not found!");
        }

        return ResponseEntity.ok(userDetails);
    }

    private HttpHeaders getHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if ("host".equalsIgnoreCase(headerName)) {
                continue;
            }
            Enumeration<String> headerValues = request.getHeaders(headerName);
            while (headerValues.hasMoreElements()) {
                String headerValue = headerValues.nextElement();
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }


}