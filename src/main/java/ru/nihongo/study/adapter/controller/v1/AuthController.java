package ru.nihongo.study.adapter.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.adapter.controller.v1.dto.auth.AuthResponse;
import ru.nihongo.study.adapter.controller.v1.dto.auth.LoginRequest;
import ru.nihongo.study.adapter.controller.v1.dto.auth.RegisterRequest;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.repository.UserInfoRepository;
import ru.nihongo.study.service.auth.JwtTokenProvider;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
//TODO: Вынести в сервис создание и получение
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserInfoRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Пользователь с таким именем уже существует");
        }

        UserInfo user = new UserInfo();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        userRepository.save(user);

        // Аутентифицируем нового пользователя
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Выписываем токен
        String token = jwtTokenProvider.createToken((UserInfo) authentication.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserInfo user = (UserInfo) authentication.getPrincipal();
            String token = jwtTokenProvider.createToken(user);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные");
        }
    }
}