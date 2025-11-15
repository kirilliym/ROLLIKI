package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Stage;
import dev.kochki.rolliki.model.entity.StageStatus;
import dev.kochki.rolliki.model.request.CreateStageRequest;
import dev.kochki.rolliki.model.request.UpdateStageRequest;
import dev.kochki.rolliki.repository.StageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StageService {

    private final StageRepository stageRepository;
    private final VideoService videoService;

    public StageService(StageRepository stageRepository, VideoService videoService) {
        this.stageRepository = stageRepository;
        this.videoService = videoService;
    }

    public Stage createStage(UUID videoId, CreateStageRequest request) {
        // Проверяем существование видео
        if (!videoService.existsById(videoId)) {
            throw new RuntimeException("Видео не найдено: " + videoId);
        }

        Stage stage = new Stage();
        stage.setVideoId(videoId);
        stage.setName(request.getName());
        stage.setComment(request.getComment());
        stage.setDeadline(request.getDeadline());
        stage.setRequiredRole(request.getRequiredRole());
        stage.setStageOrder(request.getStageOrder() != null ? request.getStageOrder() : 1);
        stage.setStatus(StageStatus.PENDING);

        return stageRepository.save(stage);
    }

    public Stage getStage(UUID stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> new RuntimeException("Этап не найден: " + stageId));
    }

    public List<Stage> getVideoStages(UUID videoId) {
        return stageRepository.findByVideoId(videoId);
    }

    public List<Stage> getStagesOrdered(UUID videoId) {
        return stageRepository.findByVideoIdOrderByStageOrder(videoId);
    }

    public List<Stage> getStagesByStatus(StageStatus status) {
        return stageRepository.findByStatus(status);
    }

    public List<Stage> getStagesByRequiredRole(dev.kochki.rolliki.model.entity.Role requiredRole) {
        return stageRepository.findByRequiredRole(requiredRole);
    }

    public Stage updateStage(UUID stageId, UpdateStageRequest request) {
        Stage stage = getStage(stageId);

        if (request.getName() != null) {
            stage.setName(request.getName());
        }
        if (request.getComment() != null) {
            stage.setComment(request.getComment());
        }
        if (request.getDeadline() != null) {
            stage.setDeadline(request.getDeadline());
        }
        if (request.getRequiredRole() != null) {
            stage.setRequiredRole(request.getRequiredRole());
        }
        if (request.getStorageAddress() != null) {
            stage.setStorageAddress(request.getStorageAddress());
        }
        if (request.getStageOrder() != null) {
            stage.setStageOrder(request.getStageOrder());
        }
        if (request.getStatus() != null) {
            stage.setStatus(request.getStatus());
        }

        return stageRepository.save(stage);
    }

    public void deleteStage(UUID stageId) {
        Stage stage = getStage(stageId);
        stageRepository.delete(stage);
    }

    public boolean existsById(UUID stageId) {
        return stageRepository.existsById(stageId);
    }
}