package dev.kochki.rolliki.model.response;

import dev.kochki.rolliki.model.entity.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponse {
    private Long id;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Integer completionPercentage;
    private VideoStatus status;
    private String title;
    private String description;
}