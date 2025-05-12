package ru.romanov.cinema.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.romanov.cinema.dtos.LoginDTO;
import ru.romanov.cinema.dtos.ProfileDTO;
import ru.romanov.cinema.dtos.UpdateProfileRequest;
import ru.romanov.cinema.dtos.UserDTO;
import ru.romanov.cinema.entites.Booking;
import ru.romanov.cinema.entites.User;
import ru.romanov.cinema.services.BookingService;
import ru.romanov.cinema.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity
                .ok(userService.authenticateUser(loginDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userDTO));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<ProfileDTO> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ProfileDTO.fromUser(user));
    }

    @GetMapping("/my-purchases")
    public ResponseEntity<List<Booking>> getPurchases(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookingService.getAllUserBookings(user.getId()));
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<ProfileDTO> updateUser(@AuthenticationPrincipal User user,
                                                 @Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
        return ResponseEntity.ok(userService.updateUser(user.getId(), updateProfileRequest));
    }

}
