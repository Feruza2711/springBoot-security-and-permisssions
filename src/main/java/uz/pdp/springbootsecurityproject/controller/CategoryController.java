package uz.pdp.springbootsecurityproject.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springbootsecurityproject.entity.Category;
import uz.pdp.springbootsecurityproject.payload.CategoryDTO;

import java.util.List;
import java.util.UUID;

@RequestMapping(CategoryController.BASE_PATH)
public interface CategoryController {
    String BASE_PATH="/api/category";

    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_ADD')")
    String add(@RequestBody CategoryDTO categoryDTO);

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_EDIT')")
    String  edit(@RequestBody CategoryDTO categoryDTO, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    String delete(@PathVariable UUID id);

    @GetMapping
    @PreAuthorize("hasAuthority('CATEGORY_LIST')")
    List<Category> getList();

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_ONE')")
    Category getOne(@PathVariable UUID id);

}
