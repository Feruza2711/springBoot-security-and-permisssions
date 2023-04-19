package uz.pdp.springbootsecurityproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.springbootsecurityproject.entity.Category;
import uz.pdp.springbootsecurityproject.payload.CategoryDTO;
import uz.pdp.springbootsecurityproject.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public String add(CategoryDTO categoryDTO) {
        Category category=new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return "ok";
    }

    public String edit(CategoryDTO categoryDTO, UUID id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if(byId.isEmpty())
            return "Error";
        Category category = byId.get();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return "ok";
    }

    public String delete(UUID id) {
        categoryRepository.deleteById(id);
        return "ok";
    }

    public List<Category> getList() {
        return categoryRepository.findAll();
    }

    public Category getOne(UUID id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isEmpty()) {
           throw new RuntimeException("category not found");
        }
        return byId.get();
    }
}
