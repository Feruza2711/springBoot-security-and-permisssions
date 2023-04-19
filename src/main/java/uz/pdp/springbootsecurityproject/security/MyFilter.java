package uz.pdp.springbootsecurityproject.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.springbootsecurityproject.entity.User;
import uz.pdp.springbootsecurityproject.repository.UserRepository;
import uz.pdp.springbootsecurityproject.utils.AppConstants;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class MyFilter extends OncePerRequestFilter {
    @Value("${app.jwt.token.access.key}")
    private String TOKEN_KEY;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AppConstants.AUTH_HEADER);

        try {
            if(Objects.nonNull(authHeader)){
                User user=null;
                if(authHeader.startsWith(AppConstants.AUTH_TYPE_BASIC)){
                    user=getUserFromBasic(authHeader);
                }
                else if(authHeader.startsWith(AppConstants.AUTH_TYPE_BEARER)){
                    user=getUserFromBearer(authHeader);
                }
                UserPrinsipal userPrinsipal=new UserPrinsipal(user);
                SecurityContextHolder.getContext().setAuthentication(
                       new UsernamePasswordAuthenticationToken(
                               userPrinsipal,
                               null,
                               userPrinsipal.getAuthorities()
                       )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);
    }

    private User getUserFromBearer(String authHeader) {
       UUID userId = null;
        try {
             userId = UUID.fromString(Jwts.parser()
                    .setSigningKey(TOKEN_KEY)
                    .parseClaimsJws(authHeader.substring(AppConstants.AUTH_TYPE_BEARER.length()))
                    .getBody()
                    .getSubject());
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    private User getUserFromBasic(String authHeader) {
        String[] basicAuthHeader = getBasicAuthHeader(authHeader);
        Optional<User> userOptional = userRepository.findByUsername(basicAuthHeader[0]);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passwordEncoder.matches(
                    basicAuthHeader[1],user.getPassword()
            )){
                return user;
            }
        }

        throw new UsernameNotFoundException("User not found");
    }

    private String[] getBasicAuthHeader(String authHeader) {
        return new String(Base64.getDecoder().decode(
                authHeader.substring(AppConstants.AUTH_TYPE_BASIC.length()))
        ).split(":");
    }
}
