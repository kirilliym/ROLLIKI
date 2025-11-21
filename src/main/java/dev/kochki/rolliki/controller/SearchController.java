package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.entity.MediaFile;
import dev.kochki.rolliki.model.entity.Project;
import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.model.request.SearchRequest;
import dev.kochki.rolliki.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/projects")
    public ResponseEntity<List<Project>> searchProjects(@RequestBody SearchRequest request) {
        try {
            List<Project> projects = searchService.searchProjects(request);
            return ResponseEntity.ok(projects);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/videos")
    public ResponseEntity<List<Video>> searchVideos(@RequestBody SearchRequest request) {
        try {
            List<Video> videos = searchService.searchVideos(request);
            return ResponseEntity.ok(videos);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/media")
    public ResponseEntity<List<MediaFile>> searchMediaFiles(@RequestBody SearchRequest request) {
        try {
            List<MediaFile> mediaFiles = searchService.searchMediaFiles(request);
            return ResponseEntity.ok(mediaFiles);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}