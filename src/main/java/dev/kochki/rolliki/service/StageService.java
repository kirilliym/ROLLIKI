package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Role;
import dev.kochki.rolliki.model.entity.Stage;
import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;
    private final VideoService videoService;

    @Transactional
    public Stage createStage(Stage stage) {
        // Если указана зависимая стадия, получаем ее уровень
        if (stage.getDependsOnStageId() != null) {
            Optional<Stage> dependsOnStage = stageRepository.findById(stage.getDependsOnStageId());
            if (dependsOnStage.isPresent()) {
                stage.setLevel(dependsOnStage.get().getLevel() + 1);
            }
        }

        Stage savedStage = stageRepository.save(stage);
        recalculateAllLevels(savedStage.getVideoId());
        updateVideoCompletionPercentage(savedStage.getVideoId());
        return savedStage;
    }

    @Transactional
    public Stage updateStage(Long id, Stage stageDetails) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found"));

        if (stageDetails.getTitle() != null) {
            stage.setTitle(stageDetails.getTitle());
        }
        if (stageDetails.getDescription() != null) {
            stage.setDescription(stageDetails.getDescription());
        }

        return stageRepository.save(stage);
    }

    @Transactional
    public Stage completeStage(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found"));

        stage.setCompleted(true);
        Stage updatedStage = stageRepository.save(stage);
        updateVideoCompletionPercentage(updatedStage.getVideoId());
        return updatedStage;
    }

    @Transactional(readOnly = true)
    public Optional<Role> getRequiredRole(Long id) {
        return stageRepository.findById(id)
                .map(Stage::getRequiredRole);
    }

    @Transactional
    public void deleteStage(Long id) {
        Stage stage = stageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stage not found"));
        Long videoId = stage.getVideoId();

        // Находим все стадии, которые зависят от удаляемой стадии
        List<Stage> dependentStages = stageRepository.findByVideoId(videoId).stream()
                .filter(s -> id.equals(s.getDependsOnStageId()))
                .toList();

        // Рекурсивно удаляем зависимые стадии
        for (Stage dependentStage : dependentStages) {
            deleteStage(dependentStage.getId());
        }

        // Удаляем саму стадию
        stageRepository.deleteById(id);
        updateVideoCompletionPercentage(videoId);
    }

    @Transactional(readOnly = true)
    public List<Stage> getStagesByVideoId(Long videoId) {
        return stageRepository.findByVideoId(videoId);
    }

    @Transactional(readOnly = true)
    public boolean checkAllStagesCompletedByLevel(Long videoId, Integer level) {
        List<Stage> stages = stageRepository.findByVideoIdAndLevel(videoId, level);
        return stages.stream().allMatch(Stage::getCompleted);
    }

    private void recalculateAllLevels(Long videoId) {
        List<Stage> stages = stageRepository.findByVideoId(videoId);

        for (Stage stage : stages) {
            if (stage.getDependsOnStageId() != null) {
                Optional<Stage> dependsOnStage = stageRepository.findById(stage.getDependsOnStageId());
                if (dependsOnStage.isPresent()) {
                    stage.setLevel(dependsOnStage.get().getLevel() + 1);
                    stageRepository.save(stage);
                }
            }
        }
    }

    private void updateVideoCompletionPercentage(Long videoId) {
        Long completedStages = stageRepository.countCompletedStagesByVideoId(videoId);
        Long totalStages = stageRepository.countTotalStagesByVideoId(videoId);

        if (totalStages > 0) {
            int percentage = (int) ((completedStages * 100) / totalStages);
            Video video = videoService.getVideoById(videoId)
                    .orElseThrow(() -> new RuntimeException("Video not found"));
            video.setCompletionPercentage(percentage);
            videoService.updateVideoCompletion(videoId, video);
        }
    }
}