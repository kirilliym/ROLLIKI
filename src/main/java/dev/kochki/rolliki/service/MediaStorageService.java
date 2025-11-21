package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.MediaFile;
import dev.kochki.rolliki.model.entity.MediaType;
import dev.kochki.rolliki.model.entity.FileCategory;
import dev.kochki.rolliki.repository.MediaFileRepository;
import dev.kochki.rolliki.repository.StageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MediaStorageService {

    @Value("${file.upload-dir:./media-storage}")
    private String storageBasePath;

    private final MediaFileRepository mediaFileRepository;
    private final StageRepository stageRepository;

    public MediaStorageService(MediaFileRepository mediaFileRepository, StageRepository stageRepository) {
        this.mediaFileRepository = mediaFileRepository;
        this.stageRepository = stageRepository;
    }

    public MediaFile storeMediaFile(UUID stageId, MultipartFile file,
                                    MediaType mediaType, FileCategory category,
                                    String description) {

        // Проверяем существование этапа
        if (!stageRepository.existsById(stageId)) {
            throw new RuntimeException("Этап не найден: " + stageId);
        }

        try {
            // Создание пути для этапа
            String stagePath = String.format("stage-%s", stageId);
            Path targetDirectory = Paths.get(storageBasePath, stagePath);
            Files.createDirectories(targetDirectory);

            // Генерация уникального имени файла
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = getFileExtension(originalFileName);
            String storedFileName = generateStoredFileName(originalFileName, mediaType, category);
            Path targetLocation = targetDirectory.resolve(storedFileName);

            // Сохранение файла
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Определение content type
            String contentType = file.getContentType();
            if (contentType == null) {
                contentType = determineContentType(fileExtension);
            }

            // Создание записи в БД
            MediaFile mediaFile = new MediaFile(
                    stageId,
                    originalFileName,
                    storedFileName,
                    targetLocation.toString(),
                    file.getSize(),
                    contentType,
                    mediaType,
                    category,
                    description
            );

            // Определение версии для FINAL файлов
            if (category == FileCategory.FINAL) {
                Integer latestVersion = getLatestFinalVersion(stageId);
                mediaFile.setVersion(latestVersion + 1);
            }

            return mediaFileRepository.save(mediaFile);

        } catch (IOException ex) {
            throw new RuntimeException("Не удалось сохранить медиафайл: " + file.getOriginalFilename(), ex);
        }
    }

    public Resource loadMediaFile(UUID mediaFileId) {
        MediaFile mediaFile = mediaFileRepository.findById(mediaFileId)
                .orElseThrow(() -> new RuntimeException("Медиафайл не найден: " + mediaFileId));

        try {
            Path filePath = Paths.get(mediaFile.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Файл не найден: " + mediaFile.getOriginalName());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Файл не найден", ex);
        }
    }

    public MediaFile getMediaFileInfo(UUID mediaFileId) {
        return mediaFileRepository.findById(mediaFileId)
                .orElseThrow(() -> new RuntimeException("Медиафайл не найден: " + mediaFileId));
    }

    public List<MediaFile> getStageMediaFiles(UUID stageId) {
        return mediaFileRepository.findByStageId(stageId);
    }

    public List<MediaFile> getStageMediaFilesByType(UUID stageId, MediaType mediaType) {
        return mediaFileRepository.findByStageIdAndMediaType(stageId, mediaType);
    }

    public List<MediaFile> getVideoMediaFiles(UUID videoId) {
        return mediaFileRepository.findByVideoId(videoId);
    }

    public List<MediaFile> getProjectMediaFiles(UUID projectId) {
        return mediaFileRepository.findByProjectId(projectId);
    }

    public MediaFile updateMediaFileDescription(UUID mediaFileId, String description) {
        MediaFile mediaFile = getMediaFileInfo(mediaFileId);
        mediaFile.setDescription(description);
        return mediaFileRepository.save(mediaFile);
    }

    public void deleteMediaFile(UUID mediaFileId) {
        MediaFile mediaFile = getMediaFileInfo(mediaFileId);

        try {
            // Удаление физического файла
            Path filePath = Paths.get(mediaFile.getFilePath());
            Files.deleteIfExists(filePath);

            // Удаление записи из БД
            mediaFileRepository.delete(mediaFile);
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось удалить медиафайл: " + mediaFile.getOriginalName(), ex);
        }
    }

    public MediaFile createThumbnail(UUID sourceMediaFileId, MultipartFile thumbnailFile) {
        MediaFile sourceMedia = getMediaFileInfo(sourceMediaFileId);

        return storeMediaFile(
                sourceMedia.getStageId(),
                thumbnailFile,
                MediaType.IMAGE,
                FileCategory.THUMBNAIL,
                "Thumbnail for: " + sourceMedia.getOriginalName()
        );
    }

    private String generateStoredFileName(String originalName, MediaType mediaType, FileCategory category) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String randomId = UUID.randomUUID().toString().substring(0, 8);
        String extension = getFileExtension(originalName);

        return String.format("%s_%s_%s_%s.%s",
                mediaType.name().toLowerCase(),
                category.name().toLowerCase(),
                timestamp,
                randomId,
                extension);
    }

    private Integer getLatestFinalVersion(UUID stageId) {
        List<MediaFile> finalVersions = mediaFileRepository.findFinalVersionsByStageId(stageId);
        return finalVersions.stream()
                .mapToInt(MediaFile::getVersion)
                .max()
                .orElse(0);
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return filename.substring(lastDotIndex + 1);
        }
        return "";
    }

    private String determineContentType(String extension) {
        switch (extension.toLowerCase()) {
            case "mp4": return "video/mp4";
            case "mov": return "video/quicktime";
            case "avi": return "video/x-msvideo";
            case "jpg": case "jpeg": return "image/jpeg";
            case "png": return "image/png";
            case "mp3": return "audio/mpeg";
            case "wav": return "audio/wav";
            default: return "application/octet-stream";
        }
    }
}