package uz.pdp.clickupsecondpart.service.impl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.CheckList;
import uz.pdp.clickupsecondpart.entity.CheckListItem;
import uz.pdp.clickupsecondpart.entity.Task;
import uz.pdp.clickupsecondpart.payload.CheckListDTO;
import uz.pdp.clickupsecondpart.payload.CheckListItemDTO;
import uz.pdp.clickupsecondpart.repository.*;
import uz.pdp.clickupsecondpart.service.CheckListService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class CheckListServiceImpl implements CheckListService {

    private final CheckListRepository checkListRepository;

    private final CheckListItemRepository checkListItemRepository;

    private final TaskRepository taskRepository;

    private final TaskUserRepository taskUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckListServiceImpl(CheckListRepository checkListRepository, CheckListItemRepository checkListItemRepository,
                                TaskRepository taskRepository, TaskUserRepository taskUserRepository,
                                UserRepository userRepository) {
        this.checkListRepository = checkListRepository;
        this.checkListItemRepository = checkListItemRepository;
        this.taskRepository = taskRepository;
        this.taskUserRepository = taskUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getCheckLists(UUID taskId) {
        return ok(checkListRepository.findAllByTask_Id(taskId));
    }

    @Override
    public ResponseEntity<?> getCheckList(UUID taskId, UUID checkListId) {
        Optional<CheckList> optionalCheckList = checkListRepository.findByTask_IdAndId(taskId, checkListId);
        if (optionalCheckList.isEmpty()) return status(NOT_FOUND).body("Task not found");
        return ok(optionalCheckList.get());
    }

    @Override
    public ResponseEntity<?> createCheckList(CheckListDTO dto) {
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        checkListRepository.save(new CheckList(dto.getName(), optionalTask.get()));
        return status(CREATED).body("Check list created successfully");
    }

    @Override
    public ResponseEntity<?> addAssignUser(UUID checkListId, UUID userId) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListId);
        if (optionalCheckList.isEmpty()) return status(NOT_FOUND).body("Check list not found");
        if (!taskUserRepository.existsByTask_IdAndUser_Id(optionalCheckList.get().getTask().getId(), userId))
            return status(BAD_REQUEST).body("This user is not allowed to view this task.");
        CheckListItem checkListItem = checkListItemRepository.getByCheckListId(checkListId);
        checkListItem.setAssignedUser(userRepository.getReferenceById(userId));
        checkListItemRepository.save(checkListItem);
        return status(CREATED).body("CheckList item assign user successfully added");
    }

    @Override
    public ResponseEntity<?> deleteCheckList(UUID checkListId) {
        if (!checkListRepository.existsById(checkListId)) return status(NOT_FOUND).body("Check list not found");
        List<CheckListItem> checkListItems = checkListItemRepository.findAllByCheckList_Id(checkListId);
        if (checkListItems.size() > 0)
            checkListItemRepository.deleteAll(checkListItems);
        checkListRepository.deleteById(checkListId);
        return status(NO_CONTENT).body("Check list deleted successfully");
    }

    @Override
    public ResponseEntity<?> updateCheckList(UUID checkListId, @Valid String name) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListId);
        if (optionalCheckList.isEmpty()) return status(NOT_FOUND).body("Check list not found");
        CheckList checkList = optionalCheckList.get();
        checkList.setName(name);
        checkListRepository.save(checkList);
        return status(CREATED).body("Check list successfully name edited");
    }

    @Override
    public ResponseEntity<?> addCheckListItem(UUID checkListId, CheckListItemDTO dto) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checkListId);
        if (optionalCheckList.isEmpty()) return status(NOT_FOUND).body("Check list not found");
        checkListItemRepository.save(new CheckListItem(
                dto.getName(),
                optionalCheckList.get(),
                dto.isResolved()
        ));
        return status(CREATED).body("Check list item created successfully");
    }

    @Override
    public ResponseEntity<?> removeCheckListItem(UUID checkListId, UUID checkListItemId) {
        if (!checkListItemRepository.existsByCheckList_IdAndId(checkListId, checkListItemId))
            return status(NOT_FOUND).body("Chekc list item not found");
        checkListItemRepository.deleteById(checkListItemId);
        return status(NO_CONTENT).body("Check list item deleted");
    }
}
