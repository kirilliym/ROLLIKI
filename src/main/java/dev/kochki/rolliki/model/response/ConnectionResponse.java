package dev.kochki.rolliki.model.response;

import dev.kochki.rolliki.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionResponse {
    private Long id;
    private Long projectId;
    private Long userId;
    private Role role;
    private Boolean accepted;
}