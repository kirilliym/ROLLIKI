package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.entity.VideoStatus;
import dev.kochki.rolliki.model.request.CreateVideoRequest;
import dev.kochki.rolliki.model.request.UpdateVideoRequest;
import dev.kochki.rolliki.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<Video> createVideo(
            @PathVariable UUID projectId,
            @RequestBody CreateVideoRequest request) {
        try {
            Video video = videoService.createVideo(projectId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(video);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Video>> getProjectVideos(@PathVariable UUID projectId) {
        try {
            List<Video> videos = videoService.getProjectVideos(projectId);
            return ResponseEntity.ok(videos);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideo(
            @PathVariable UUID projectId,
            @PathVariable UUID videoId) {
        try {
            Video video = videoService.getVideo(videoId);
            return ResponseEntity.ok(video);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Video>> getVideosByStatus(
            @PathVariable UUID projectId,
            @PathVariable VideoStatus status) {
        try {
            List<Video> videos = videoService.getVideosByStatus(status);
            return ResponseEntity.ok(videos);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(
            @PathVariable UUID projectId,
            @PathVariable UUID videoId,
            @RequestBody UpdateVideoRequest request) {
        try {
            Video updatedVideo = videoService.updateVideo(videoId, request);
            return ResponseEntity.ok(updatedVideo);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(
            @PathVariable UUID projectId,
            @PathVariable UUID videoId) {
        try {
            videoService.deleteVideo(videoId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}