package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.Role;

public class UpdateProjectMemberRequest {

    private String email;
    private Role role;
    private Integer efficiency;

    // Конструкторы
    public UpdateProjectMemberRequest() {}

    public UpdateProjectMemberRequest(String email, Role role, Integer efficiency) {
        this.email = email;
        this.role = role;
        this.efficiency = efficiency;
    }

    // Геттеры и сеттеры
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Integer getEfficiency() { return efficiency; }
    public void setEfficiency(Integer efficiency) { this.efficiency = efficiency; }
}