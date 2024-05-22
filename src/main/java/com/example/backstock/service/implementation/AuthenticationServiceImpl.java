package com.example.backstock.service.implementation;

import com.example.backstock.dto.AuthResponseDto;
import com.example.backstock.dto.LoginDto;
import com.example.backstock.entity.Role;
import com.example.backstock.entity.UserEntity;
import com.example.backstock.entity.Validation;
import com.example.backstock.error.BadRequestException;
import com.example.backstock.repository.RoleRepository;
import com.example.backstock.repository.UserEntityRepo;
import com.example.backstock.security.JwtGenerator;
import com.example.backstock.service.interfaces.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserEntityRepo userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Validation validation;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtGenerator jwtGenerator;
    @Value("${refresh.token.secret}")
    private String refreshTokenSecret;
    @Value("${access.token.expiration}")
    private Long accessTokenExpiration;
    @Value("${access.token.secret}")
    private String accessTokenSecret;

    @Override
    public String register(UserEntity user, String roleName) {
        if (!validation.usernameValidation(user.getUserName()) || !validation.passwordValidation(user.getPassword()) ||
                !validation.emailValidation(user.getEmailAddress()))
            throw new BadRequestException("Username, email-address or password invalid");
        if (this.userRepository.existsByUserNameOrEmailAddress(user.getUserName(), user.getEmailAddress()))
            throw new BadRequestException("Username or " +
                    "email-address already used");
        Role role = this.roleRepository.findByRoleName(roleName);
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return "User saved successfully";
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        Authentication authentication = getAuthentication(loginDto.getUserName(), loginDto.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateTokens(authentication);
    }

    @Override
    public AuthResponseDto refreshToken(HttpServletRequest request) {
        String token = jwtGenerator.getJWTFromRequest(request);
        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token, refreshTokenSecret)) {
            UserEntity userEntity = this.getUser(jwtGenerator.getUsernameFromToken(token, refreshTokenSecret));
            return new AuthResponseDto(this.generateAccessToken(userEntity), null, "New access token");
        }
        return new AuthResponseDto(null, null, "Refresh token invalid");
    }
    

    @Override
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) SecurityContextHolder.clearContext();
        return "Logged out";
    }

    private String generateAccessToken(UserEntity userEntity) {
        Date currentDate = new Date();
        Claims claims = Jwts.claims().setSubject(userEntity.getUserName());
        claims.put("roles", userEntity.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + this.accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, accessTokenSecret)
                .compact();
    }

    private Authentication getAuthentication(String userName, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        return authentication;
    }

    private UserEntity getUser(String userName) {
        return this.userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("Bad Credential"));
    }
}
