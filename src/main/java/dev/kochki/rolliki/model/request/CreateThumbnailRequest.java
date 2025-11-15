package dev.kochki.rolliki.model.request;

public class CreateThumbnailRequest {

    private String description;

    // Конструкторы
    public CreateThumbnailRequest() {}

    public CreateThumbnailRequest(String description) {
        this.description = description;
    }

    // Геттеры и сеттеры
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}