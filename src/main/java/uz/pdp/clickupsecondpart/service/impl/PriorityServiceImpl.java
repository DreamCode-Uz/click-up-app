package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Priority;
import uz.pdp.clickupsecondpart.payload.PriorityDTO;
import uz.pdp.clickupsecondpart.repository.IconsRepository;
import uz.pdp.clickupsecondpart.repository.PriorityRepository;
import uz.pdp.clickupsecondpart.service.PriorityService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class PriorityServiceImpl implements PriorityService {

    private final PriorityRepository priorityRepository;

    private final IconsRepository iconsRepository;

    @Autowired
    public PriorityServiceImpl(PriorityRepository priorityRepository, IconsRepository iconsRepository) {
        this.priorityRepository = priorityRepository;
        this.iconsRepository = iconsRepository;
    }

    @Override
    public ResponseEntity<?> getPriorities() {
        return ok(priorityRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getPriority(Long priorityId) {
        Optional<Priority> optionalPriority = priorityRepository.findById(priorityId);
        if (optionalPriority.isPresent())
            return ok(optionalPriority.get());
        return status(NOT_FOUND).body("Priority not found");
    }

    @Override
    public ResponseEntity<?> addPriority(PriorityDTO priorityDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> updatePriority(Long priorityId) {
        return null;
    }
}
