package com.holydev.fastcase.utilities.customs;

import com.holydev.fastcase.entities.User;
import com.holydev.fastcase.utilities.primitives.SimpleUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


@RequiredArgsConstructor
@Component
public class CustomTokenDeployment {
    private final JwtEncoder jwtEncoder;


    public SimpleUser generateSimpleUserWithToken(User user) {
        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(joining(" "));


        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://127.0.0.1:8081")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(format("%s,%s", user.getId(), user.getUsername()))
                .claim("roles", scope)
                .build();

        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new SimpleUser(user, token);
    }
}
