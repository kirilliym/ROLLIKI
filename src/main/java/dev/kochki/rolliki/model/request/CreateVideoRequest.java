package dev.kochki.rolliki.model.request;

import java.util.UUID;

public class CreateVideoRequest {

    private UUID projectId;
    private String name;
    private String description;

    // Конструкторы
    public CreateVideoRequest() {}

    public CreateVideoRequest(UUID projectId, String name, String description) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    // Геттеры и сеттеры
    public UUID getProjectId() { return projectId; }
    public void setProjectId(UUID projectId) { this.projectId = projectId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}