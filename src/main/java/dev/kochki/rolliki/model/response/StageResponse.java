package dev.kochki.rolliki.model.response;

import dev.kochki.rolliki.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageResponse {
    private Long id;
    private Long videoId;
    private String title;
    private String description;
    private Role requiredRole;
    private Long dependsOnStageId;
    private Boolean completed;
    private Integer level;
}