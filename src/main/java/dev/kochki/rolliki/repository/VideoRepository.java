package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.projectId = :projectId ORDER BY " +
            "CASE WHEN v.status = 'IN_PROGRESS' THEN 0 ELSE 1 END, " +
            "v.deadline ASC")
    List<Video> findByProjectIdOrderByStatusAndDeadline(@Param("projectId") Long projectId);

    List<Video> findByProjectId(Long projectId);
}