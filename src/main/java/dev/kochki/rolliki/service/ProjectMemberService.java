package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.ProjectMember;
import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.model.request.CreateProjectMemberRequest;
import dev.kochki.rolliki.model.request.UpdateProjectMemberRequest;
import dev.kochki.rolliki.repository.ProjectMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectService projectService;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                                ProjectService projectService) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectService = projectService;
    }

    public ProjectMember createMember(UUID projectId, CreateProjectMemberRequest request) {
        // Проверяем существование проекта
        if (!projectService.existsById(projectId)) {
            throw new RuntimeException("Проект не найден: " + projectId);
        }

        // Проверяем уникальность email в проекте
        if (projectMemberRepository.existsByEmailAndProjectId(request.getEmail(), projectId)) {
            throw new RuntimeException("Участник с email " + request.getEmail() + " уже существует в проекте");
        }

        ProjectMember member = new ProjectMember();
        member.setEmail(request.getEmail());
        member.setPassword(request.getPassword()); // В реальном приложении хэшируйте пароль!
        member.setProjectId(projectId);
        member.setRole(request.getRole());
        member.setEfficiency(request.getEfficiency() != null ? request.getEfficiency() : 85);

        return projectMemberRepository.save(member);
    }

    public ProjectMember getMember(UUID memberId) {
        return projectMemberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Участник не найден: " + memberId));
    }

    public List<ProjectMember> getProjectMembers(UUID projectId) {
        return projectMemberRepository.findByProjectId(projectId);
    }

    public List<ProjectMember> getMembersByRole(Role role) {
        return projectMemberRepository.findByRole(role);
    }

    public ProjectMember updateMember(UUID memberId, UpdateProjectMemberRequest request) {
        ProjectMember member = getMember(memberId);

        if (request.getEmail() != null) {
            member.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            member.setRole(request.getRole());
        }
        if (request.getEfficiency() != null) {
            member.setEfficiency(request.getEfficiency());
        }

        return projectMemberRepository.save(member);
    }

    public void deleteMember(UUID memberId) {
        ProjectMember member = getMember(memberId);
        projectMemberRepository.delete(member);
    }

    public ProjectMember findByEmail(String email) {
        return projectMemberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Участник с email не найден: " + email));
    }
}