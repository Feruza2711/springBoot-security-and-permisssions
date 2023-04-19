package uz.pdp.springbootsecurityproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springbootsecurityproject.entity.Role;
import uz.pdp.springbootsecurityproject.entity.enums.RoleTypeEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleTypeEnum(RoleTypeEnum user);
}
