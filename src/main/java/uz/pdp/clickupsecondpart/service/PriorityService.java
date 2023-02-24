package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.payload.PriorityDTO;

public interface PriorityService {

    ResponseEntity<?> getPriorities();

    ResponseEntity<?> getPriority(Long priorityId);

    ResponseEntity<?> addPriority(PriorityDTO priorityDTO);

    ResponseEntity<?> updatePriority(Long priorityId, PriorityDTO priorityDTO);
}
