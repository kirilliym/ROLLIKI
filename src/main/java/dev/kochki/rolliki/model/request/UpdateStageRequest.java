package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.model.entity.StageStatus;

import java.time.LocalDateTime;

public class UpdateStageRequest {

    private String name;
    private String comment;
    private LocalDateTime deadline;
    private Role requiredRole;
    private String storageAddress;
    private Integer stageOrder;
    private StageStatus status;

    // Конструкторы
    public UpdateStageRequest() {}

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public Role getRequiredRole() { return requiredRole; }
    public void setRequiredRole(Role requiredRole) { this.requiredRole = requiredRole; }

    public String getStorageAddress() { return storageAddress; }
    public void setStorageAddress(String storageAddress) { this.storageAddress = storageAddress; }

    public Integer getStageOrder() { return stageOrder; }
    public void setStageOrder(Integer stageOrder) { this.stageOrder = stageOrder; }

    public StageStatus getStatus() { return status; }
    public void setStatus(StageStatus status) { this.status = status; }
}