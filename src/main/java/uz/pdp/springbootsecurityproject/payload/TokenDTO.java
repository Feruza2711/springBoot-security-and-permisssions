package uz.pdp.springbootsecurityproject.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.springbootsecurityproject.utils.AppConstants;

@Builder
@Data
public class TokenDTO {
    private final String refreshToken;

    private final String accessToken;

    private final String tokenType = AppConstants.AUTH_TYPE_BEARER;

    public TokenDTO(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
