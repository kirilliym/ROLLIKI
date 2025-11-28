package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.entity.VideoStatus;
import dev.kochki.rolliki.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    @Transactional
    public Video createVideo(Video video) {
        video.setCompletionPercentage(0);
        video.setStatus(VideoStatus.IN_PROGRESS);
        return videoRepository.save(video);
    }

    @Transactional
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    @Transactional
    public Video updateVideo(Long id, Video videoDetails) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        if (videoDetails.getTitle() != null) {
            video.setTitle(videoDetails.getTitle());
        }
        if (videoDetails.getDescription() != null) {
            video.setDescription(videoDetails.getDescription());
        }
        if (videoDetails.getDeadline() != null) {
            video.setDeadline(videoDetails.getDeadline());
        }

        return videoRepository.save(video);
    }

    @Transactional(readOnly = true)
    public List<Video> getVideosByProjectId(Long projectId) {
        return videoRepository.findByProjectIdOrderByStatusAndDeadline(projectId);
    }

    @Transactional(readOnly = true)
    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    @Transactional
    public Video updateVideoCompletion(Long id, Video videoDetails) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        video.setCompletionPercentage(videoDetails.getCompletionPercentage());

        return videoRepository.save(video);
    }
}