package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateStageRequest {

    private UUID videoId;
    private String name;
    private String comment;
    private LocalDateTime deadline;
    private Role requiredRole;
    private Integer stageOrder = 1;

    // Конструкторы
    public CreateStageRequest() {}

    public CreateStageRequest(UUID videoId, String name, Role requiredRole, Integer stageOrder) {
        this.videoId = videoId;
        this.name = name;
        this.requiredRole = requiredRole;
        this.stageOrder = stageOrder;
    }

    // Геттеры и сеттеры
    public UUID getVideoId() { return videoId; }
    public void setVideoId(UUID videoId) { this.videoId = videoId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public Role getRequiredRole() { return requiredRole; }
    public void setRequiredRole(Role requiredRole) { this.requiredRole = requiredRole; }

    public Integer getStageOrder() { return stageOrder; }
    public void setStageOrder(Integer stageOrder) { this.stageOrder = stageOrder; }
}