package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.Stage;
import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.entity.VideoStatus;
import dev.kochki.rolliki.repository.StageRepository;
import dev.kochki.rolliki.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final StageRepository stageRepository;
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

    @Transactional
    public List<Video> getVideosByProjectId(Long projectId) {
        List<Video> videos = videoRepository.findByProjectIdOrderByStatusAndDeadline(projectId);

        for (Video video : videos) {
            updateVideoStatus(video);
        }

//        List<Video> videosToDelete = videos.stream()
//                .filter(video -> {
//                    List<Stage> stages = stageRepository.findByVideoId(video.getId());
//                    return stages.isEmpty();
//                })
//                .collect(Collectors.toList());

//        for (Video video : videosToDelete) {
//            videoRepository.delete(video);
//        }

        return videos;
//        return videos.stream()
//                .filter(video -> !videosToDelete.contains(video))
//                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Video> getVideoById(Long id) {
        Optional<Video> videoOptional = videoRepository.findById(id);

        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            updateVideoStatus(video);
        }

        return videoOptional;
    }

    private void updateVideoStatus(Video video) {
        VideoStatus newStatus = calculateVideoStatus(video);
        if (video.getStatus() != newStatus) {
            video.setStatus(newStatus);
            videoRepository.save(video);
        }
    }

    private VideoStatus calculateVideoStatus(Video video) {
        if (video.getCompletionPercentage() == 100) {
            return VideoStatus.COMPLETED;
        }

        if (video.getDeadline().isBefore(LocalDateTime.now())) {
            return VideoStatus.OVERDUE;
        }

        return VideoStatus.IN_PROGRESS;
    }

    @Transactional
    public Video updateVideoCompletion(Long id, Video videoDetails) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        video.setCompletionPercentage(videoDetails.getCompletionPercentage());

        return videoRepository.save(video);
    }
}