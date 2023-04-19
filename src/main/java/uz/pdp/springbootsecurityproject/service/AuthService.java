package uz.pdp.springbootsecurityproject.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.springbootsecurityproject.entity.User;
import uz.pdp.springbootsecurityproject.entity.enums.RoleTypeEnum;
import uz.pdp.springbootsecurityproject.exceptions.RestExeption;
import uz.pdp.springbootsecurityproject.payload.ApiResponse;
import uz.pdp.springbootsecurityproject.payload.SignDTO;
import uz.pdp.springbootsecurityproject.payload.TokenDTO;
import uz.pdp.springbootsecurityproject.repository.RoleRepository;
import uz.pdp.springbootsecurityproject.repository.UserRepository;
import uz.pdp.springbootsecurityproject.security.UserPrinsipal;
import uz.pdp.springbootsecurityproject.utils.Constants;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
                       @Lazy PasswordEncoder passwordEncoder, JavaMailSender javaMailSender,
                       @Lazy  AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
    }

    public  ApiResponse<String> emailVerification(String code) {
        User user = userRepository.findByVerificationCode(code).orElseThrow();
        if(user.isEnabled()){
            RestExeption.restThrow("This user already exist");
        }
        user.setEnabled(true);
        userRepository.save(user);
        return ApiResponse.succesResponce("Successfully active");
    }

    public ApiResponse<String>  signUp(SignDTO signDTO) {
        if(userRepository.existsByUsername(signDTO.getUsername())){
            throw RestExeption.restThrow("This user already exsist");
        }
        User user=new User(
                signDTO.getUsername(),
                passwordEncoder.encode(signDTO.getPassword()),
                roleRepository.findByRoleTypeEnum(RoleTypeEnum.USER).orElseThrow()
        );
        UUID code=UUID.randomUUID();
        user.setVerificationCode(code.toString());
        user.setEnabled(false);
        userRepository.save(user);
        CompletableFuture.runAsync(()->sendVerificationCodeToEmail(user));
        return ApiResponse.succesResponce("ok");
    }

    private void sendVerificationCodeToEmail(User user) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(Constants.sender);
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("");
        mailMessage.setText("YOUR_CODE"+"http://localhost/api/auth/verification-email/" + user.getVerificationCode());
        javaMailSender.send(mailMessage);
    }


    public ApiResponse<TokenDTO> signIn(SignDTO signDTO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signDTO.getUsername(),
                        signDTO.getPassword()
                )
        );

        UserPrinsipal userPrincipal =(UserPrinsipal) authenticate.getPrincipal();
        Date accessExpirationDate = new Date(System.currentTimeMillis()
                + Constants.ACCESS_TOKEN_EXPIRATION);
        String accessToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, Constants.ACCESS_TOKEN_KEY)
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(accessExpirationDate)
                .compact();
        Date refreshExpirationDate = new Date(System.currentTimeMillis()
                + Constants.ACCESS_TOKEN_EXPIRATION);

        String refreshToken = Jwts
                .builder()
                .signWith(SignatureAlgorithm.HS256, Constants.REFRESH_TOKEN_KEY)
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(refreshExpirationDate)
                .compact();

        return ApiResponse.successResponse(
                TokenDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
        );

    }
}


