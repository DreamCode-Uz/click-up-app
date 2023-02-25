package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Icons;
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
        if (priorityRepository.existsByName(priorityDTO.getName()))
            return status(NOT_FOUND).body("Priority already exists");
        Optional<Icons> optionalIcons = iconsRepository.findById(priorityDTO.getIconId());
        if (optionalIcons.isEmpty()) return status(NOT_FOUND).body("Icon not found");
        Priority save = priorityRepository.save(new Priority(priorityDTO.getName(), priorityDTO.getName(), optionalIcons.get()));
        return status(CREATED).body(save);
    }

    @Override
    public ResponseEntity<?> updatePriority(Long priorityId, PriorityDTO dto) {
        Optional<Priority> optionalPriority = priorityRepository.findById(priorityId);
        if (optionalPriority.isEmpty()) return status(NOT_FOUND).body("Priority not found");
        Priority priority = optionalPriority.get();
        priority.setName(dto.getName());
        priority.setColor(dto.getColor());
        Optional<Icons> optionalIcons = iconsRepository.findById(dto.getIconId());
        optionalIcons.ifPresent(priority::setIcon);
        priority = priorityRepository.save(priority);
        return status(CREATED).body(priority);
    }
}
