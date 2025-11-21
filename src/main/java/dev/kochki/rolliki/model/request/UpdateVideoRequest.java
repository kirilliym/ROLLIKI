package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.VideoStatus;

public class UpdateVideoRequest {

    private String name;
    private String description;
    private VideoStatus status;

    // Конструкторы
    public UpdateVideoRequest() {}

    public UpdateVideoRequest(String name, String description, VideoStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public VideoStatus getStatus() { return status; }
    public void setStatus(VideoStatus status) { this.status = status; }
}