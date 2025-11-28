package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findByVideoId(Long videoId);

    @Query("SELECT s FROM Stage s WHERE s.videoId = :videoId AND s.level = :level")
    List<Stage> findByVideoIdAndLevel(@Param("videoId") Long videoId, @Param("level") Integer level);

    @Query("SELECT COUNT(s) FROM Stage s WHERE s.videoId = :videoId AND s.completed = true")
    Long countCompletedStagesByVideoId(@Param("videoId") Long videoId);

    @Query("SELECT COUNT(s) FROM Stage s WHERE s.videoId = :videoId")
    Long countTotalStagesByVideoId(@Param("videoId") Long videoId);

    List<Stage> findByDependsOnStageId(Long dependsOnStageId);
}