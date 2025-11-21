package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.MediaType;
import dev.kochki.rolliki.model.entity.FileCategory;

import java.util.UUID;

public class MediaFilterRequest {
    private UUID stageId;
    private UUID videoId;
    private UUID projectId;
    private MediaType mediaType;
    private FileCategory category;

    // Конструкторы
    public MediaFilterRequest() {}

    // Геттеры и сеттеры
    public UUID getStageId() { return stageId; }
    public void setStageId(UUID stageId) { this.stageId = stageId; }

    public UUID getVideoId() { return videoId; }
    public void setVideoId(UUID videoId) { this.videoId = videoId; }

    public UUID getProjectId() { return projectId; }
    public void setProjectId(UUID projectId) { this.projectId = projectId; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public FileCategory getCategory() { return category; }
    public void setCategory(FileCategory category) { this.category = category; }
}