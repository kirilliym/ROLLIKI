package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.mapper.UserMapper;
import dev.kochki.rolliki.model.request.ChangePasswordRequest;
import dev.kochki.rolliki.model.request.ChangeUsernameRequest;
import dev.kochki.rolliki.model.request.UserRequest;
import dev.kochki.rolliki.model.response.UserResponse;
import dev.kochki.rolliki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        try {
            var userDTO = userService.register(userRequest);
            return ResponseEntity.ok(userMapper.mapToResponse(userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
        return userService.login(userRequest)
                .map(userDTO -> ResponseEntity.ok(userMapper.mapToResponse(userDTO)))
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userDTO -> ResponseEntity.ok(userMapper.mapToResponse(userDTO)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean success = userService.changePassword(id, changePasswordRequest);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<Void> changeUsername(@PathVariable Long id, @RequestBody ChangeUsernameRequest changeUsernameRequest) {
        boolean success = userService.changeUsername(id, changeUsernameRequest);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}