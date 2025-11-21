package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Stage;
import dev.kochki.rolliki.model.entity.StageStatus;
import dev.kochki.rolliki.model.request.CreateStageRequest;
import dev.kochki.rolliki.model.request.UpdateStageRequest;
import dev.kochki.rolliki.service.StageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/videos/{videoId}/stages")
@CrossOrigin(origins = "*")
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @PostMapping
    public ResponseEntity<Stage> createStage(
            @PathVariable UUID videoId,
            @RequestBody CreateStageRequest request) {
        try {
            Stage stage = stageService.createStage(videoId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(stage);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Stage>> getVideoStages(@PathVariable UUID videoId) {
        try {
            List<Stage> stages = stageService.getVideoStages(videoId);
            return ResponseEntity.ok(stages);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{stageId}")
    public ResponseEntity<Stage> getStage(
            @PathVariable UUID videoId,
            @PathVariable UUID stageId) {
        try {
            Stage stage = stageService.getStage(stageId);
            return ResponseEntity.ok(stage);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<Stage>> getStagesOrdered(@PathVariable UUID videoId) {
        try {
            List<Stage> stages = stageService.getStagesOrdered(videoId);
            return ResponseEntity.ok(stages);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Stage>> getStagesByStatus(
            @PathVariable UUID videoId,
            @PathVariable StageStatus status) {
        try {
            List<Stage> stages = stageService.getStagesByStatus(status);
            return ResponseEntity.ok(stages);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{stageId}")
    public ResponseEntity<Stage> updateStage(
            @PathVariable UUID videoId,
            @PathVariable UUID stageId,
            @RequestBody UpdateStageRequest request) {
        try {
            Stage updatedStage = stageService.updateStage(stageId, request);
            return ResponseEntity.ok(updatedStage);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{stageId}")
    public ResponseEntity<Void> deleteStage(
            @PathVariable UUID videoId,
            @PathVariable UUID stageId) {
        try {
            stageService.deleteStage(stageId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}