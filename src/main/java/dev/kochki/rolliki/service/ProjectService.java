package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Project;
import dev.kochki.rolliki.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Проект не найден: " + projectId));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject(UUID projectId, Project projectDetails) {
        Project project = getProject(projectId);
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        return projectRepository.save(project);
    }

    public void deleteProject(UUID projectId) {
        Project project = getProject(projectId);
        projectRepository.delete(project);
    }

    public boolean existsById(UUID projectId) {
        return projectRepository.existsById(projectId);
    }
}