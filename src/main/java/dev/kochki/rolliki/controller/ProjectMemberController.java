package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.ProjectMember;
import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.model.request.CreateProjectMemberRequest;
import dev.kochki.rolliki.model.request.UpdateProjectMemberRequest;
import dev.kochki.rolliki.service.ProjectMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@CrossOrigin(origins = "*")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    public ResponseEntity<ProjectMember> createMember(
            @PathVariable UUID projectId,
            @RequestBody CreateProjectMemberRequest request) {
        try {
            ProjectMember member = projectMemberService.createMember(projectId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(member);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectMember>> getProjectMembers(@PathVariable UUID projectId) {
        try {
            List<ProjectMember> members = projectMemberService.getProjectMembers(projectId);
            return ResponseEntity.ok(members);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ProjectMember> getMember(
            @PathVariable UUID projectId,
            @PathVariable UUID memberId) {
        try {
            ProjectMember member = projectMemberService.getMember(memberId);
            return ResponseEntity.ok(member);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<ProjectMember>> getMembersByRole(
            @PathVariable UUID projectId,
            @PathVariable Role role) {
        try {
            List<ProjectMember> members = projectMemberService.getMembersByRole(role);
            return ResponseEntity.ok(members);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ProjectMember> updateMember(
            @PathVariable UUID projectId,
            @PathVariable UUID memberId,
            @RequestBody UpdateProjectMemberRequest request) {
        try {
            ProjectMember updatedMember = projectMemberService.updateMember(memberId, request);
            return ResponseEntity.ok(updatedMember);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable UUID projectId,
            @PathVariable UUID memberId) {
        try {
            projectMemberService.deleteMember(memberId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}