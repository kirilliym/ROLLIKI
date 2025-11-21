package dev.kochki.rolliki.model.dto;

import dev.kochki.rolliki.model.entity.MediaFile;
import dev.kochki.rolliki.model.entity.MediaType;
import dev.kochki.rolliki.model.entity.FileCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public class MediaFileResponse {
    private UUID id;
    private UUID stageId;
    private String originalName;
    private String storedName;
    private Long fileSize;
    private String contentType;
    private MediaType mediaType;
    private FileCategory category;
    private String description;
    private Integer version;
    private LocalDateTime createdAt;
    private String error;

    // Конструкторы
    public MediaFileResponse() {}

    public static MediaFileResponse fromEntity(MediaFile mediaFile) {
        MediaFileResponse response = new MediaFileResponse();
        response.setId(mediaFile.getId());
        response.setStageId(mediaFile.getStageId());
        response.setOriginalName(mediaFile.getOriginalName());
        response.setStoredName(mediaFile.getStoredName());
        response.setFileSize(mediaFile.getFileSize());
        response.setContentType(mediaFile.getContentType());
        response.setMediaType(mediaFile.getMediaType());
        response.setCategory(mediaFile.getCategory());
        response.setDescription(mediaFile.getDescription());
        response.setVersion(mediaFile.getVersion());
        response.setCreatedAt(mediaFile.getCreatedAt());
        return response;
    }

    public static MediaFileResponse error(String errorMessage) {
        MediaFileResponse response = new MediaFileResponse();
        response.setError(errorMessage);
        return response;
    }

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getStageId() { return stageId; }
    public void setStageId(UUID stageId) { this.stageId = stageId; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public String getStoredName() { return storedName; }
    public void setStoredName(String storedName) { this.storedName = storedName; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public FileCategory getCategory() { return category; }
    public void setCategory(FileCategory category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}