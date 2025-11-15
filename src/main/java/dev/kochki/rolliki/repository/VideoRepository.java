package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.entity.VideoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    List<Video> findByProjectId(UUID projectId);
    List<Video> findByStatus(VideoStatus status);
    List<Video> findByProjectIdAndStatus(UUID projectId, VideoStatus status);
}