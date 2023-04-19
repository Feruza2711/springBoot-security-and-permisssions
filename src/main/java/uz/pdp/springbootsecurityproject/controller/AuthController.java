package uz.pdp.springbootsecurityproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springbootsecurityproject.entity.User;
import uz.pdp.springbootsecurityproject.entity.enums.RoleTypeEnum;
import uz.pdp.springbootsecurityproject.exceptions.RestExeption;
import uz.pdp.springbootsecurityproject.payload.ApiResponse;
import uz.pdp.springbootsecurityproject.payload.SignDTO;
import uz.pdp.springbootsecurityproject.payload.TokenDTO;
import uz.pdp.springbootsecurityproject.repository.RoleRepository;
import uz.pdp.springbootsecurityproject.repository.UserRepository;
import uz.pdp.springbootsecurityproject.service.AuthService;
import uz.pdp.springbootsecurityproject.utils.AppConstants;

import javax.management.relation.RoleInfo;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static uz.pdp.springbootsecurityproject.controller.AuthController.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class AuthController {
    public static final String BASE_PATH=AppConstants.BASE_PATH+"/auth";
    public static final String SIGN_UP_PATH = "/sign-up";
    public static final String SIGN_IN_PATH = "/sign-in";
    public static final String EMAIL_VERIFICATION_PATH = "email-verification/{code}";
    private final AuthService authService;

    @PostMapping(SIGN_UP_PATH)
    public ApiResponse<String> signUp(@RequestBody SignDTO signDTO) {
        return authService.signUp(signDTO);
    }


    @PostMapping(SIGN_IN_PATH)
    public ApiResponse<TokenDTO> signIn(@RequestBody SignDTO signDTO){
        return authService.signIn(signDTO);

    }
    @GetMapping(EMAIL_VERIFICATION_PATH)
    public ApiResponse<String> emailVerification(@PathVariable String code){
        return authService.emailVerification(code);
    }





}
