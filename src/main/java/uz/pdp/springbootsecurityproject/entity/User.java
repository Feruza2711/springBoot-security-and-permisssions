package uz.pdp.springbootsecurityproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.springbootsecurityproject.entity.templete.AbsUUIDEntity;


import java.util.UUID;
@Table(name = "users")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends AbsUUIDEntity {

@Column(nullable = false,unique = true)
    private String username;

@Column(nullable = false)
    private String password;

@ManyToOne(optional = false)
    private Role role;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private String verificationCode;

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
