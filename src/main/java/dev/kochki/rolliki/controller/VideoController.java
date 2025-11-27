package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.request.CreateVideoRequest;
import dev.kochki.rolliki.model.request.UpdateVideoRequest;
import dev.kochki.rolliki.model.response.VideoResponse;
import dev.kochki.rolliki.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoResponse> createVideo(@RequestBody CreateVideoRequest request) {
        Video video = new Video();
        video.setProjectId(request.getProjectId());
        video.setDeadline(request.getDeadline());
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());

        Video createdVideo = videoService.createVideo(video);
        VideoResponse response = new VideoResponse(
                createdVideo.getId(),
                createdVideo.getProjectId(),
                createdVideo.getCreatedAt(),
                createdVideo.getDeadline(),
                createdVideo.getCompletionPercentage(),
                createdVideo.getStatus(),
                createdVideo.getTitle(),
                createdVideo.getDescription()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id, @RequestBody UpdateVideoRequest request) {
        Video videoDetails = new Video();
        videoDetails.setTitle(request.getTitle());
        videoDetails.setDescription(request.getDescription());
        videoDetails.setDeadline(request.getDeadline());

        Video updatedVideo = videoService.updateVideo(id, videoDetails);
        VideoResponse response = new VideoResponse(
                updatedVideo.getId(),
                updatedVideo.getProjectId(),
                updatedVideo.getCreatedAt(),
                updatedVideo.getDeadline(),
                updatedVideo.getCompletionPercentage(),
                updatedVideo.getStatus(),
                updatedVideo.getTitle(),
                updatedVideo.getDescription()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<VideoResponse>> getVideosByProjectId(@PathVariable Long projectId) {
        List<Video> videos = videoService.getVideosByProjectId(projectId);
        List<VideoResponse> responses = videos.stream()
                .map(video -> new VideoResponse(
                        video.getId(),
                        video.getProjectId(),
                        video.getCreatedAt(),
                        video.getDeadline(),
                        video.getCompletionPercentage(),
                        video.getStatus(),
                        video.getTitle(),
                        video.getDescription()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id)
                .map(video -> new VideoResponse(
                        video.getId(),
                        video.getProjectId(),
                        video.getCreatedAt(),
                        video.getDeadline(),
                        video.getCompletionPercentage(),
                        video.getStatus(),
                        video.getTitle(),
                        video.getDescription()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}