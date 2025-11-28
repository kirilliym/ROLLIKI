package dev.kochki.rolliki.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "required_role", nullable = false)
    private Role requiredRole;

    @Column(name = "depends_on_stage_id")
    private Long dependsOnStageId;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Column(name = "level", nullable = false)
    private Integer level = 0;
}