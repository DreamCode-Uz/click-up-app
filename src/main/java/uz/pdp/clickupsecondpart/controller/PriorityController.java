package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.payload.PriorityDTO;
import uz.pdp.clickupsecondpart.service.PriorityService;
import uz.pdp.clickupsecondpart.service.impl.PriorityServiceImpl;

@RestController
@RequestMapping("/api/priority")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Priority Controller")
public class PriorityController implements PriorityService {

    private final PriorityServiceImpl service;

    @Autowired
    public PriorityController(PriorityServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getPriorities() {
        return service.getPriorities();
    }

    @GetMapping("/{priorityId}")
    @Override
    public ResponseEntity<?> getPriority(@PathVariable Long priorityId) {
        return service.getPriority(priorityId);
    }

    @PostMapping
    @Override
    public ResponseEntity<?> addPriority(@RequestBody @Valid PriorityDTO priorityDTO) {
        return service.addPriority(priorityDTO);
    }

    @PutMapping("/{priorityId}")
    @Override
    public ResponseEntity<?> updatePriority(@PathVariable Long priorityId, @RequestBody @Valid PriorityDTO priorityDTO) {
        return service.updatePriority(priorityId, priorityDTO);
    }
}
