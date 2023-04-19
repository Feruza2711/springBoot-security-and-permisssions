package uz.pdp.springbootsecurityproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.pdp.springbootsecurityproject.entity.enums.PermissionEnum;
import uz.pdp.springbootsecurityproject.entity.enums.RoleTypeEnum;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false,unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,unique = true)
    private RoleTypeEnum roleTypeEnum;

@Enumerated(EnumType.STRING)
@ElementCollection
    private Set<PermissionEnum> permissions;

    public Role(String name, RoleTypeEnum roleTypeEnum, Set<PermissionEnum> permissions) {
        this.name = name;
        this.roleTypeEnum = roleTypeEnum;
        this.permissions = permissions;
    }
}
