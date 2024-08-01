package tech.getarrays.associationsministry.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.getarrays.associationsministry.config.JwtService;
import tech.getarrays.associationsministry.model.Role;
import tech.getarrays.associationsministry.model.User;
import tech.getarrays.associationsministry.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponce register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .user_role(Role.USER)
                .build();
        repository.save(user);
        // generate Token
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .accessToken(jwToken)
                .build();
    }

    public AuthenticationResponce authenticate(authenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        // generate Token
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .accessToken(jwToken)
                .build();
    }
}
