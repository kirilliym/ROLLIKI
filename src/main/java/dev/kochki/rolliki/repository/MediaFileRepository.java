package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.MediaFile;
import dev.kochki.rolliki.model.entity.MediaType;
import dev.kochki.rolliki.model.entity.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, UUID> {

    List<MediaFile> findByStageId(UUID stageId);

    List<MediaFile> findByStageIdAndMediaType(UUID stageId, MediaType mediaType);

    List<MediaFile> findByStageIdAndCategory(UUID stageId, FileCategory category);

    Optional<MediaFile> findByStageIdAndStoredName(UUID stageId, String storedName);

    @Query("SELECT mf FROM MediaFile mf WHERE mf.stageId IN (SELECT s.id FROM Stage s WHERE s.videoId = :videoId)")
    List<MediaFile> findByVideoId(@Param("videoId") UUID videoId);

    @Query("SELECT mf FROM MediaFile mf WHERE mf.stageId IN (SELECT s.id FROM Stage s WHERE s.videoId IN (SELECT v.id FROM Video v WHERE v.projectId = :projectId))")
    List<MediaFile> findByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT mf FROM MediaFile mf WHERE mf.stageId = :stageId AND mf.category = 'FINAL' ORDER BY mf.version DESC")
    List<MediaFile> findFinalVersionsByStageId(@Param("stageId") UUID stageId);
}