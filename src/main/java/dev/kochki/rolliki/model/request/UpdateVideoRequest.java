package dev.kochki.rolliki.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoRequest {
    private String title;
    private String description;
    private LocalDateTime deadline;
}