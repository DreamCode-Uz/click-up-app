package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.StatusDTO;

public interface StatusService {

    ResponseEntity<?> getStatus();

    ResponseEntity<?> getStatus(Long statusId);

    ResponseEntity<?> addStatus(StatusDTO dto, User user);

    ResponseEntity<?> updateStatus(Long statusId, StatusDTO dto, User user);

    ResponseEntity<?> deleteStatus(Long statusId, User user);
}
