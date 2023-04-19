package uz.pdp.springbootsecurityproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springbootsecurityproject.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category,UUID> {
}
