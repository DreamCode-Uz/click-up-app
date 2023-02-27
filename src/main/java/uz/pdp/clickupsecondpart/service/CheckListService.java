package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.payload.CheckListDTO;
import uz.pdp.clickupsecondpart.payload.CheckListItemDTO;

import java.util.UUID;

public interface CheckListService {

    ResponseEntity<?> getCheckLists(UUID taskId);

    ResponseEntity<?> getCheckList(UUID taskId, UUID checkListId);

    ResponseEntity<?> createCheckList(CheckListDTO dto);

    ResponseEntity<?> addAssignUser(UUID checkListId, UUID userId);

    ResponseEntity<?> deleteCheckList(UUID checkListId);

    ResponseEntity<?> updateCheckList(UUID checkListId, String name);

    ResponseEntity<?> addCheckListItem(UUID checkListId, CheckListItemDTO dto);

    ResponseEntity<?> removeCheckListItem(UUID checkListId, UUID checkListItemId);
}
