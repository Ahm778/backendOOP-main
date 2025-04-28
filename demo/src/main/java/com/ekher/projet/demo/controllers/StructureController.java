package com.ekher.projet.demo.controllers;



import com.ekher.projet.demo.dto.StructureDto;
import com.ekher.projet.demo.services.StructureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/structures")
public class StructureController {
    private final StructureService structureService;

    @Autowired
    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }


    @GetMapping
    public ResponseEntity<List<StructureDto>> getAllStructures() {
        List<StructureDto> response = structureService.getAllStructures();
        return ResponseEntity.ok(response);
    }



    @PostMapping
    public ResponseEntity<StructureDto> createStructure(@RequestBody StructureDto structureDto) {
        StructureDto response = structureService.createStructure(structureDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStructure(@PathVariable long id) {
        structureService.deleteStructure(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}