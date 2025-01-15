package com.tennis.platform.controller;

import com.tennis.platform.dto.CoachDTO;
import com.tennis.platform.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@CrossOrigin(origins = "http://localhost:8080")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping
    public ResponseEntity<List<CoachDTO>> getAllCoaches() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachDTO> getCoachById(@PathVariable Long id) {
        return ResponseEntity.ok(coachService.getCoachById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachDTO> updateCoachProfile(
        @PathVariable Long id,
        @RequestBody CoachDTO coachDTO
    ) {
        return ResponseEntity.ok(coachService.updateCoach(id, coachDTO));
    }
} 