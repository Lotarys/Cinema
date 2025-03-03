package ru.romanov.cinema.services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.romanov.cinema.dtos.LoginDTO;
import ru.romanov.cinema.dtos.UserDTO;
import ru.romanov.cinema.entites.Role;
import ru.romanov.cinema.entites.User;
import ru.romanov.cinema.exceptions.UserAlreadyExistsException;
import ru.romanov.cinema.exceptions.UserCreationException;
import ru.romanov.cinema.repositories.RoleRepository;
import ru.romanov.cinema.repositories.UserRepository;


@Slf4j
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository usersRepo;
    private final RoleRepository roleRepo;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserService(PasswordEncoder passwordEncoder, UserRepository usersRepo, RoleRepository roleRepo, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepo = usersRepo;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public String createUser(UserDTO userDTO) {
        if (usersRepo.existsByEmail(userDTO.email())) {
            throw new UserAlreadyExistsException("User with email '" + userDTO.email() + "' already exists");
        }
        User user = createUserEntity(userDTO);
        try {
            usersRepo.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserCreationException("User creation failed due to data integrity issues", ex);
        }
        log.info("User created: {}", user.getEmail());
        return jwtService.generateToken(user);
    }

    public String authenticateUser(LoginDTO loginDTO) {
        User user = (User) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.login(),
                        loginDTO.password()
                )
        ).getPrincipal();
        log.info("User authenticated: {}", user.getEmail());
        return jwtService.generateToken(user);
    }

    private User createUserEntity(UserDTO dto) {
        return User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(checkRole(dto))
                .build();
    }

    private Role checkRole(UserDTO userDTO) {
        return roleRepo.findByName(userDTO.role())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }
}

