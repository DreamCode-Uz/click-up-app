package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.payload.CheckListDTO;
import uz.pdp.clickupsecondpart.payload.CheckListItemDTO;
import uz.pdp.clickupsecondpart.service.CheckListService;
import uz.pdp.clickupsecondpart.service.impl.CheckListServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Check list and Item Controller")
public class CheckListController implements CheckListService {

    private final CheckListServiceImpl service;

    @Autowired
    public CheckListController(CheckListServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/task/{taskId}")
    @Override
    public ResponseEntity<?> getCheckLists(@PathVariable UUID taskId) {
        return service.getCheckLists(taskId);
    }

    @GetMapping("/{checkListId}/task/{taskId}/")
    @Override
    public ResponseEntity<?> getCheckList(@PathVariable UUID taskId, @PathVariable UUID checkListId) {
        return service.getCheckList(taskId, checkListId);
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createCheckList(@RequestBody @Valid CheckListDTO dto) {
        return service.createCheckList(dto);
    }

    @PostMapping("/{checkListId}/user/{userId}")
    @Override
    public ResponseEntity<?> addAssignUser(@PathVariable UUID checkListId, @PathVariable UUID userId) {
        return service.addAssignUser(checkListId, userId);
    }

    @DeleteMapping("/{checkListId}")
    @Override
    public ResponseEntity<?> deleteCheckList(@PathVariable UUID checkListId) {
        return service.deleteCheckList(checkListId);
    }

    @PutMapping("/{checkListId}")
    @Override
    public ResponseEntity<?> updateCheckList(@PathVariable UUID checkListId, @RequestParam(name = "name") String name) {
        return service.updateCheckList(checkListId, name);
    }

    @PostMapping("/{checkListId}/item")
    @Override
    public ResponseEntity<?> addCheckListItem(@PathVariable UUID checkListId, @RequestBody @Valid CheckListItemDTO dto) {
        return service.addCheckListItem(checkListId, dto);
    }

    @DeleteMapping("/{checkListId}/item/{checkListItemId}")
    @Override
    public ResponseEntity<?> removeCheckListItem(@PathVariable UUID checkListId, @PathVariable UUID checkListItemId) {
        return service.removeCheckListItem(checkListId, checkListItemId);
    }
}
