package com.lloyds.onboard.service;

import com.lloyds.onboard.entity.JourneyTemplate;
import com.lloyds.onboard.repository.JourneyTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyTemplateService {

    @Autowired
    private JourneyTemplateRepository journeyRepo;

    public List<JourneyTemplate> getAllJourneys() {
        return journeyRepo.findAll();
    }

    public Optional<JourneyTemplate> getJourneyById(Long id) {
        return journeyRepo.findById(id);
    }

    public JourneyTemplate createJourney(JourneyTemplate journey) {
        return journeyRepo.save(journey);
    }

    public Optional<JourneyTemplate> updateJourney(Long id, JourneyTemplate updated) {
        return journeyRepo.findById(id).map(existing -> {
            existing.setJourneytype(updated.getJourneytype());
            existing.setVersion(updated.getVersion());
            existing.setTemplatedata(updated.getTemplatedata());
            return journeyRepo.save(existing);
        });
    }

    public boolean deleteJourney(Long id) {
        if (journeyRepo.existsById(id)) {
            journeyRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
