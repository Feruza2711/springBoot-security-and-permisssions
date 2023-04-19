package uz.pdp.springbootsecurityproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignDTO {
    private String username;
    private String password;
}
