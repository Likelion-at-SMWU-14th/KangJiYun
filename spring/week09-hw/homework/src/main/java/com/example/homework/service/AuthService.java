package com.example.homework.service;

import com.example.homework.dto.LoginRequest;
import com.example.homework.dto.SignupRequest;
import com.example.homework.dto.TokenResponse;
import com.example.homework.entity.Member;
import com.example.homework.entity.RefreshToken;
import com.example.homework.jwt.JwtTokenProvider;
import com.example.homework.repository.MemberRepository;
import com.example.homework.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signup(SignupRequest request) {

        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name()
        );

        memberRepository.save(member);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        String email = authentication.getName();
        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        saveRefreshToken(email, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    public TokenResponse reissue(String token) {

        if (token == null
                || !jwtTokenProvider.validateToken(token)
                || !JwtTokenProvider.REFRESH.equals(jwtTokenProvider.getType(token))) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다."
            );
        }

        String email = jwtTokenProvider.getEmail(token);

        RefreshToken stored = refreshTokenRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "로그인 상태가 아닙니다."
                ));

        if (!stored.getToken().equals(token)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "이미 사용되었거나 일치하지 않는 Refresh Token입니다."
            );
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(email);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(email);

        stored.updateToken(newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    private void saveRefreshToken(String email, String token) {
        refreshTokenRepository.findByEmail(email)
                .ifPresentOrElse(
                        stored -> stored.updateToken(token),
                        () -> refreshTokenRepository.save(new RefreshToken(email, token))
                );
    }
}