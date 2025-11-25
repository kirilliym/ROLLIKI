package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Connection;
import dev.kochki.rolliki.model.entity.Project;
import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.repository.ConnectionRepository;
import dev.kochki.rolliki.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ConnectionRepository connectionRepository;
    public Project createProject(Project project) {
        Project savedProject = projectRepository.save(project);

        Connection ownerConnection = new Connection();
        ownerConnection.setProjectId(savedProject.getId());
        ownerConnection.setUserId(savedProject.getOwnerId());
        ownerConnection.setRole(Role.OWNER);
        ownerConnection.setAccepted(true);

        connectionRepository.save(ownerConnection);

        return savedProject;
    }
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Transactional
    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setUrl(projectDetails.getUrl());

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}