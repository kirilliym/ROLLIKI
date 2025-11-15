package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.entity.VideoStatus;
import dev.kochki.rolliki.model.request.CreateVideoRequest;
import dev.kochki.rolliki.model.request.UpdateVideoRequest;
import dev.kochki.rolliki.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final ProjectService projectService;

    public VideoService(VideoRepository videoRepository, ProjectService projectService) {
        this.videoRepository = videoRepository;
        this.projectService = projectService;
    }

    public Video createVideo(UUID projectId, CreateVideoRequest request) {
        // Проверяем существование проекта
        if (!projectService.existsById(projectId)) {
            throw new RuntimeException("Проект не найден: " + projectId);
        }

        Video video = new Video();
        video.setProjectId(projectId);
        video.setName(request.getName());
        video.setDescription(request.getDescription());
        video.setStatus(VideoStatus.IN_PROGRESS);

        return videoRepository.save(video);
    }

    public Video getVideo(UUID videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Видео не найдено: " + videoId));
    }

    public List<Video> getProjectVideos(UUID projectId) {
        return videoRepository.findByProjectId(projectId);
    }

    public List<Video> getVideosByStatus(VideoStatus status) {
        return videoRepository.findByStatus(status);
    }

    public List<Video> getProjectVideosByStatus(UUID projectId, VideoStatus status) {
        return videoRepository.findByProjectIdAndStatus(projectId, status);
    }

    public Video updateVideo(UUID videoId, UpdateVideoRequest request) {
        Video video = getVideo(videoId);

        if (request.getName() != null) {
            video.setName(request.getName());
        }
        if (request.getDescription() != null) {
            video.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            video.setStatus(request.getStatus());
        }

        return videoRepository.save(video);
    }

    public void deleteVideo(UUID videoId) {
        Video video = getVideo(videoId);
        videoRepository.delete(video);
    }

    public boolean existsById(UUID videoId) {
        return videoRepository.existsById(videoId);
    }
}