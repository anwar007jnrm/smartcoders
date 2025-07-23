package com.lloyds.rm.controller;
import com.lloyds.rm.entity.JourneyTemplate;
import com.lloyds.rm.service.JourneyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journeys")
public class JourneyTemplateController {

    @Autowired
    private JourneyTemplateService journeyService;

    @GetMapping
    public List<JourneyTemplate> getAllJourneys() {
        return journeyService.getAllJourneys();
    }

    @PostMapping
    public JourneyTemplate createJourney(@RequestBody JourneyTemplate journey) {
        return journeyService.createJourney(journey);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JourneyTemplate> updateJourney(@PathVariable Long id, @RequestBody JourneyTemplate updated) {
        return journeyService.updateJourney(id, updated)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJourney(@PathVariable Long id) {
        boolean deleted = journeyService.deleteJourney(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
