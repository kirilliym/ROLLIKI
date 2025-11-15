package dev.kochki.rolliki.controller;

import dev.kochki.rolliki.model.dto.MediaFileResponse;
import dev.kochki.rolliki.model.entity.MediaFile;
import dev.kochki.rolliki.model.entity.MediaType;
import dev.kochki.rolliki.model.entity.FileCategory;
import dev.kochki.rolliki.model.request.UpdateDescriptionRequest;
import dev.kochki.rolliki.service.MediaStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stages/{stageId}/media")
@CrossOrigin(origins = "*")
public class MediaController {

    private final MediaStorageService mediaStorageService;

    public MediaController(MediaStorageService mediaStorageService) {
        this.mediaStorageService = mediaStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<MediaFileResponse> uploadMediaFile(
            @PathVariable UUID stageId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("mediaType") MediaType mediaType,
            @RequestParam("category") FileCategory category,
            @RequestParam(value = "description", required = false) String description) {

        try {
            MediaFile mediaFile = mediaStorageService.storeMediaFile(
                    stageId, file, mediaType, category, description);

            MediaFileResponse response = MediaFileResponse.fromEntity(mediaFile);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MediaFileResponse.error(ex.getMessage()));
        }
    }

    @GetMapping("/{mediaFileId}/download")
    public ResponseEntity<Resource> downloadMediaFile(
            @PathVariable UUID stageId,
            @PathVariable UUID mediaFileId) {

        try {
            Resource resource = mediaStorageService.loadMediaFile(mediaFileId);
            MediaFile mediaFile = mediaStorageService.getMediaFileInfo(mediaFileId);

            String contentType = mediaFile.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + mediaFile.getOriginalName() + "\"")
                    .body(resource);

        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{mediaFileId}/preview")
    public ResponseEntity<Resource> previewMediaFile(
            @PathVariable UUID stageId,
            @PathVariable UUID mediaFileId) {

        try {
            Resource resource = mediaStorageService.loadMediaFile(mediaFileId);
            MediaFile mediaFile = mediaStorageService.getMediaFileInfo(mediaFileId);

            String contentType = mediaFile.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(resource);

        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MediaFileResponse>> getStageMediaFiles(
            @PathVariable UUID stageId,
            @RequestParam(required = false) MediaType mediaType,
            @RequestParam(required = false) FileCategory category) {

        try {
            List<MediaFile> mediaFiles;
            if (mediaType != null && category != null) {
                mediaFiles = mediaStorageService.getStageMediaFilesByType(stageId, mediaType)
                        .stream()
                        .filter(mf -> mf.getCategory() == category)
                        .collect(Collectors.toList());
            } else if (mediaType != null) {
                mediaFiles = mediaStorageService.getStageMediaFilesByType(stageId, mediaType);
            } else if (category != null) {
                mediaFiles = mediaStorageService.getStageMediaFiles(stageId)
                        .stream()
                        .filter(mf -> mf.getCategory() == category)
                        .collect(Collectors.toList());
            } else {
                mediaFiles = mediaStorageService.getStageMediaFiles(stageId);
            }

            List<MediaFileResponse> responses = mediaFiles.stream()
                    .map(MediaFileResponse::fromEntity)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responses);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<MediaFileResponse>> getVideoMediaFiles(@PathVariable UUID videoId) {
        try {
            List<MediaFile> mediaFiles = mediaStorageService.getVideoMediaFiles(videoId);
            List<MediaFileResponse> responses = mediaFiles.stream()
                    .map(MediaFileResponse::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<MediaFileResponse>> getProjectMediaFiles(@PathVariable UUID projectId) {
        try {
            List<MediaFile> mediaFiles = mediaStorageService.getProjectMediaFiles(projectId);
            List<MediaFileResponse> responses = mediaFiles.stream()
                    .map(MediaFileResponse::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{mediaFileId}/description")
    public ResponseEntity<MediaFileResponse> updateDescription(
            @PathVariable UUID stageId,
            @PathVariable UUID mediaFileId,
            @RequestBody UpdateDescriptionRequest request) {

        try {
            MediaFile mediaFile = mediaStorageService.updateMediaFileDescription(
                    mediaFileId, request.getDescription());

            return ResponseEntity.ok(MediaFileResponse.fromEntity(mediaFile));

        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{mediaFileId}")
    public ResponseEntity<Void> deleteMediaFile(
            @PathVariable UUID stageId,
            @PathVariable UUID mediaFileId) {

        try {
            mediaStorageService.deleteMediaFile(mediaFileId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{mediaFileId}/thumbnail")
    public ResponseEntity<MediaFileResponse> createThumbnail(
            @PathVariable UUID stageId,
            @PathVariable UUID mediaFileId,
            @RequestParam("thumbnail") MultipartFile thumbnailFile) {

        try {
            MediaFile thumbnail = mediaStorageService.createThumbnail(mediaFileId, thumbnailFile);
            return ResponseEntity.ok(MediaFileResponse.fromEntity(thumbnail));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}