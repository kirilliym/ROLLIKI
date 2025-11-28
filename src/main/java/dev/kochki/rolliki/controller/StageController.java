package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.model.entity.Stage;
import dev.kochki.rolliki.model.request.CreateStageRequest;
import dev.kochki.rolliki.model.request.UpdateStageRequest;
import dev.kochki.rolliki.model.response.StageResponse;
import dev.kochki.rolliki.service.StageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stage")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @PostMapping
    public ResponseEntity<StageResponse> createStage(@RequestBody CreateStageRequest request) {
        Stage stage = new Stage();
        stage.setVideoId(request.getVideoId());
        stage.setTitle(request.getTitle());
        stage.setDescription(request.getDescription());
        stage.setRequiredRole(request.getRequiredRole());
        stage.setDependsOnStageId(request.getDependsOnStageId());

        Stage createdStage = stageService.createStage(stage);
        StageResponse response = new StageResponse(
                createdStage.getId(),
                createdStage.getVideoId(),
                createdStage.getTitle(),
                createdStage.getDescription(),
                createdStage.getRequiredRole(),
                createdStage.getDependsOnStageId(),
                createdStage.getCompleted(),
                createdStage.getLevel()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StageResponse> updateStage(@PathVariable Long id, @RequestBody UpdateStageRequest request) {
        Stage stageDetails = new Stage();
        stageDetails.setTitle(request.getTitle());
        stageDetails.setDescription(request.getDescription());

        Stage updatedStage = stageService.updateStage(id, stageDetails);
        StageResponse response = new StageResponse(
                updatedStage.getId(),
                updatedStage.getVideoId(),
                updatedStage.getTitle(),
                updatedStage.getDescription(),
                updatedStage.getRequiredRole(),
                updatedStage.getDependsOnStageId(),
                updatedStage.getCompleted(),
                updatedStage.getLevel()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<StageResponse> completeStage(@PathVariable Long id) {
        Stage completedStage = stageService.completeStage(id);
        StageResponse response = new StageResponse(
                completedStage.getId(),
                completedStage.getVideoId(),
                completedStage.getTitle(),
                completedStage.getDescription(),
                completedStage.getRequiredRole(),
                completedStage.getDependsOnStageId(),
                completedStage.getCompleted(),
                completedStage.getLevel()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/role")
    public ResponseEntity<Role> getRequiredRole(@PathVariable Long id) {
        return stageService.getRequiredRole(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        stageService.deleteStage(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<StageResponse>> getStagesByVideoId(@PathVariable Long videoId) {
        List<Stage> stages = stageService.getStagesByVideoId(videoId);
        List<StageResponse> responses = stages.stream()
                .map(stage -> new StageResponse(
                        stage.getId(),
                        stage.getVideoId(),
                        stage.getTitle(),
                        stage.getDescription(),
                        stage.getRequiredRole(),
                        stage.getDependsOnStageId(),
                        stage.getCompleted(),
                        stage.getLevel()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/video/{videoId}/level/{level}/completed")
    public ResponseEntity<Boolean> checkAllStagesCompletedByLevel(
            @PathVariable Long videoId,
            @PathVariable Integer level) {
        boolean allCompleted = stageService.checkAllStagesCompletedByLevel(videoId, level);
        return ResponseEntity.ok(allCompleted);
    }
}