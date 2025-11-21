package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.Stage;
import dev.kochki.rolliki.model.entity.StageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StageRepository extends JpaRepository<Stage, UUID> {
    List<Stage> findByVideoId(UUID videoId);
    List<Stage> findByVideoIdOrderByStageOrder(UUID videoId);
    List<Stage> findByStatus(StageStatus status);
    List<Stage> findByRequiredRole(dev.kochki.rolliki.model.entity.Role requiredRole);
}