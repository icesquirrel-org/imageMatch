package com.example.demo.sample.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/sample")
@Slf4j
public class SampleController {

	@Autowired
	SampleServiceImpl service;
	
	@PostMapping(value = "", consumes = {APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
    public Map<String, Object> compare(@RequestBody final Map options)  {
        return Map.of("result", service.getCompareResult(options));
    }
	@PostMapping(value = "/test", consumes = {APPLICATION_JSON_VALUE, APPLICATION_JSON_UTF8_VALUE})
    public Map<String, Object> compared(@RequestBody final Map options)  {
        return Map.of("result", "");
    }
	
	
}