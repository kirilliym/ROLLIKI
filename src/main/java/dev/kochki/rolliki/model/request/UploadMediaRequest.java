package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.MediaType;
import dev.kochki.rolliki.model.entity.FileCategory;

public class UploadMediaRequest {

    private MediaType mediaType;
    private FileCategory category;
    private String description;

    // Конструкторы
    public UploadMediaRequest() {}

    public UploadMediaRequest(MediaType mediaType, FileCategory category, String description) {
        this.mediaType = mediaType;
        this.category = category;
        this.description = description;
    }

    // Геттеры и сеттеры
    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public FileCategory getCategory() { return category; }
    public void setCategory(FileCategory category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}