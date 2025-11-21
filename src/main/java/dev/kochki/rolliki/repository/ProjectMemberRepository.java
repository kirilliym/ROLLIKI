package dev.kochki.rolliki.repository;

import dev.kochki.rolliki.model.entity.ProjectMember;
import dev.kochki.rolliki.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {
    List<ProjectMember> findByProjectId(UUID projectId);
    List<ProjectMember> findByRole(Role role);
    Optional<ProjectMember> findByEmail(String email);
    boolean existsByEmailAndProjectId(String email, UUID projectId);
}