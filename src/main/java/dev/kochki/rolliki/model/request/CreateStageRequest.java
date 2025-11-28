package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStageRequest {
    private Long videoId;
    private String title;
    private String description;
    private Role requiredRole;
    private Long dependsOnStageId;
}