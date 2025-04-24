package com.ekher.projet.demo.controllers;

import com.ekher.projet.demo.dto.EmployerDto;
import com.ekher.projet.demo.services.EmployerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/v1/employers")
public class EmployerController {
    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }



    @GetMapping
    public ResponseEntity<List<EmployerDto>> getAllEmployers() {
        List<EmployerDto> response = employerService.getAllEmployers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<EmployerDto> createEmployer(@RequestBody EmployerDto employerDto) {
        EmployerDto response = employerService.createEmployer(employerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable long id) {
        employerService.deleteEmployer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}