package com.tour.booking.tyme.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.core.util.Json;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public String check() throws JsonProcessingException {
        return Json.mapper().writeValueAsString(Map.of("status", "ok"));
    }
}
