package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectionRequest {
    private Long projectId;
    private Long userId;
    private Role role;
}