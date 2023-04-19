package uz.pdp.springbootsecurityproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springbootsecurityproject.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByVerificationCode(String code);
}
