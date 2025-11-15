package dev.kochki.rolliki.model.request;

import dev.kochki.rolliki.model.entity.Role;

public class CreateProjectMemberRequest {

    private String email;
    private String password;
    private Role role;
    private Integer efficiency = 85;

    // Конструкторы
    public CreateProjectMemberRequest() {}

    public CreateProjectMemberRequest(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.efficiency = 85;
    }

    // Геттеры и сеттеры
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Integer getEfficiency() { return efficiency; }
    public void setEfficiency(Integer efficiency) { this.efficiency = efficiency; }
}