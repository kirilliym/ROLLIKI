package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByProjectId(Long projectId);
    List<Connection> findByUserIdAndAcceptedFalse(Long userId);
    List<Connection> findByUserIdAndAcceptedTrue(Long userId);
    List<Connection> findByProjectIdAndAcceptedFalse(Long projectId);
    List<Connection> findByProjectIdAndAcceptedTrue(Long projectId);
    Connection findByUserIdAndProjectId(Long userId, Long projectId);
}