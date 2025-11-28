package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/help")
public class HelpController {

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAvailableRoles() {
        List<Role> roles = Arrays.asList(Role.values());
        return ResponseEntity.ok(roles);
    }
}