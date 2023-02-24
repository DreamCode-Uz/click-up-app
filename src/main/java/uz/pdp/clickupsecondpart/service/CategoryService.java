package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.CategoryDTO;

public interface CategoryService {

    ResponseEntity<?> getCategory(Long categoryId);

    ResponseEntity<?> addCategory(CategoryDTO dto, User user);

    ResponseEntity<?> editCategory(Long categoryId, CategoryDTO dto, User user);

    ResponseEntity<?> deleteCategory(Long categoryId, User user);
}
