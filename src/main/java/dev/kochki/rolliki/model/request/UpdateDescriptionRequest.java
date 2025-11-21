package dev.kochki.rolliki.model.request;

public class UpdateDescriptionRequest {

    private String description;

    // Конструкторы
    public UpdateDescriptionRequest() {}

    public UpdateDescriptionRequest(String description) {
        this.description = description;
    }

    // Геттеры и сеттеры
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}