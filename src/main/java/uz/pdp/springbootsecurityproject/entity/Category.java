package uz.pdp.springbootsecurityproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.springbootsecurityproject.entity.templete.AbsUUIDEntity;
@Entity
@Getter
@Setter
public class Category extends AbsUUIDEntity {

    @Column(nullable = false,unique = true)
    private String name;
}
