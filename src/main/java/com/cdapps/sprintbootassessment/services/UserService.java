package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.UserDao;
import com.cdapps.sprintbootassessment.dto.AuthenticationRequestDto;
import com.cdapps.sprintbootassessment.dto.AuthenticationResponseDto;
import com.cdapps.sprintbootassessment.enums.Role;
import com.cdapps.sprintbootassessment.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    final UserDao userDao;
    final JwtEncoder encoder;
    final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public UserService(UserDao userDao, JwtEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public void saveUser(AuthenticationRequestDto dto) {
        final User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        userDao.save(user);
    }

    public AuthenticationResponseDto generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;

        List<String> scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return new AuthenticationResponseDto(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(), expiry, scope);
    }
}
