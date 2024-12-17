package org.sd.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ReverseProxy {
    private final RestTemplate restTemplate;
    private final String[] USER_INSTANCES = {
            "http://request-reply-communication-users-1:8080",
            "http://request-reply-communication-users-2:8080",
            "http://request-reply-communication-users-3:8080"
    };
    private final String DEVICES_SERVICE_URL = "http://devices:8080";

    private final AtomicInteger userInstanceIndex = new AtomicInteger(0);

    public ReverseProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getNextUserInstanceUrl() {
        int index = userInstanceIndex.getAndUpdate(i -> (i + 1) % USER_INSTANCES.length);
        return USER_INSTANCES[index];
    }

    @PostMapping("/users/**")
    public ResponseEntity<String> proxyPostUser(HttpServletRequest request, @RequestBody String user) {
        String path = request.getRequestURI();
        String url = getNextUserInstanceUrl() + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<String> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    @DeleteMapping("/users/**")
    public ResponseEntity<String> proxyDeleteUser(HttpServletRequest request) {
        String path = request.getRequestURI();
        String url = getNextUserInstanceUrl() + path;

        HttpHeaders headers = getHttpHeaders(request);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
    }

    @GetMapping("/users/**")
    public ResponseEntity<String> proxyGetUser(HttpServletRequest request) {
        String path = request.getRequestURI();
        String url = getNextUserInstanceUrl() + path;

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

}