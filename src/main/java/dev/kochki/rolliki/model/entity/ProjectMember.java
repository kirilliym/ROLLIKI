package dev.kochki.rolliki.model.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "project_members")
public class ProjectMember {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Integer efficiency = 85;

    // Конструкторы
    public ProjectMember() {}

    public ProjectMember(String email, String password, UUID projectId, Role role) {
        this.email = email;
        this.password = password;
        this.projectId = projectId;
        this.role = role;
    }

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UUID getProjectId() { return projectId; }
    public void setProjectId(UUID projectId) { this.projectId = projectId; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Integer getEfficiency() { return efficiency; }
    public void setEfficiency(Integer efficiency) { this.efficiency = efficiency; }
}