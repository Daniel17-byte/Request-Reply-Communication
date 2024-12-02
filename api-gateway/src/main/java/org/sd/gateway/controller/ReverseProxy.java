package org.sd.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ReverseProxy {
    private final RestTemplate restTemplate;
    private final String USERS_SERVICE_URL = "http://users:8080";
    private final String DEVICES_SERVICE_URL = "http://devices:8080";

    public ReverseProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/users/**")
    public ResponseEntity<String> proxyPostUser(HttpServletRequest request, @RequestBody String user) {
        String path = request.getRequestURI();
        String url = USERS_SERVICE_URL + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @GetMapping("/users/**")
    public ResponseEntity<String> proxyGetUser(HttpServletRequest request) {
        String path = request.getRequestURI();
        String url = USERS_SERVICE_URL + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @PostMapping("/devices/**")
    public ResponseEntity<String> proxyPostDevice(HttpServletRequest request) {
        String path = request.getRequestURI();
        String url = DEVICES_SERVICE_URL + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @GetMapping("/devices/**")
    public ResponseEntity<String> proxyGetDevice(HttpServletRequest request) {
        String path = request.getRequestURI();
        String url = DEVICES_SERVICE_URL + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
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

    @PostMapping("/auth/**")
    public ResponseEntity<String> proxyPostAuth(HttpServletRequest request, @RequestBody String body) {
        String path = request.getRequestURI().replace("/api", "");
        String url = USERS_SERVICE_URL + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @GetMapping("/auth/**")
    public ResponseEntity<String> proxyGetAuth(HttpServletRequest request) {
        String path = request.getRequestURI().replace("/api", "");
        String url = USERS_SERVICE_URL + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

}