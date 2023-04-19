package uz.pdp.springbootsecurityproject.entity.templete;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass
@Getter
public class AbsUUIDEntity {
    @Id
    @GeneratedValue(generator = "uuid_my_id")
    @GenericGenerator(name = "uuid_my_id", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
}
