package uz.pdp.springbootsecurityproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springbootsecurityproject.entity.Category;
import uz.pdp.springbootsecurityproject.payload.CategoryDTO;
import uz.pdp.springbootsecurityproject.repository.CategoryRepository;
import uz.pdp.springbootsecurityproject.service.CategoryService;

import java.util.List;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController{
    private final CategoryService categoryService;
    @Override
    public String add(CategoryDTO categoryDTO) {
        return categoryService.add(categoryDTO);
    }

    @Override
    public String edit(CategoryDTO categoryDTO, UUID id) {
        return categoryService.edit(categoryDTO,id);
    }

    @Override
    public String delete(UUID id) {
        return categoryService.delete(id);
    }

    @Override
    public List<Category> getList() {
        return categoryService.getList();
    }

    @Override
    public Category getOne(UUID id) {

        return categoryService.getOne(id);
    }
}
