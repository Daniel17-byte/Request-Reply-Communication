package org.sd.gateway.service;

import org.sd.gateway.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final RestTemplate restTemplate;

    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        String userServiceUrl = "http://users:8080/api/users/username/" + username;

        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<User> response = restTemplate.exchange(userServiceUrl, HttpMethod.GET, entity, User.class);

            if (response.getBody() == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            return response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UsernameNotFoundException("Failed to fetch user: " + username, e);
        }
    }
}