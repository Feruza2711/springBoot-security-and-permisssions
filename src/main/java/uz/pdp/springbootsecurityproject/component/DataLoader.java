package uz.pdp.springbootsecurityproject.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.springbootsecurityproject.entity.Role;
import uz.pdp.springbootsecurityproject.entity.User;
import uz.pdp.springbootsecurityproject.entity.enums.PermissionEnum;
import uz.pdp.springbootsecurityproject.entity.enums.RoleTypeEnum;
import uz.pdp.springbootsecurityproject.repository.RoleRepository;
import uz.pdp.springbootsecurityproject.repository.UserRepository;

import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;

    @Override
    public void run(String... args) throws Exception {
        if(Objects.equals(ddlMode,"create")){
            Role admin = roleRepository.save(new Role(
                    "ADMIN",
                    RoleTypeEnum.ADMIN,
                    Set.of(PermissionEnum.values())
            ));

            Role user = roleRepository.save(new Role(
                    "USER",
                    RoleTypeEnum.USER,
                    Set.of(PermissionEnum.CATEGORY_ONE,
                            PermissionEnum.CATEGORY_LIST)
            ));

            userRepository.save(new User(
                    "admin@admin.com",
                    passwordEncoder.encode("123"),
                    admin
            ));
        }

    }
}
