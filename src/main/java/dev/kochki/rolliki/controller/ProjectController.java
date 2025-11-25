package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Project;
import dev.kochki.rolliki.model.request.CreateProjectRequest;
import dev.kochki.rolliki.model.request.UpdateProjectRequest;
import dev.kochki.rolliki.model.response.ProjectResponse;
import dev.kochki.rolliki.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setOwnerId(request.getOwnerId());

        Project createdProject = projectService.createProject(project);
        ProjectResponse response = new ProjectResponse(
                createdProject.getId(),
                createdProject.getName(),
                createdProject.getDescription(),
                createdProject.getUrl(),
                createdProject.getOwnerId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectResponse> responses = projects.stream()
                .map(project -> new ProjectResponse(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getUrl(),
                        project.getOwnerId()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(project -> new ProjectResponse(
                        project.getId(),
                        project.getName(),
                        project.getDescription(),
                        project.getUrl(),
                        project.getOwnerId()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody UpdateProjectRequest request) {
        Project projectDetails = new Project();
        projectDetails.setName(request.getName());
        projectDetails.setDescription(request.getDescription());
        projectDetails.setUrl(request.getUrl());

        Project updatedProject = projectService.updateProject(id, projectDetails);
        ProjectResponse response = new ProjectResponse(
                updatedProject.getId(),
                updatedProject.getName(),
                updatedProject.getDescription(),
                updatedProject.getUrl(),
                updatedProject.getOwnerId()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}