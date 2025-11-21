package dev.kochki.rolliki.service;

import dev.kochki.rolliki.model.entity.MediaFile;
import dev.kochki.rolliki.model.entity.Project;
import dev.kochki.rolliki.model.entity.Video;
import dev.kochki.rolliki.repository.MediaFileRepository;
import dev.kochki.rolliki.repository.ProjectRepository;
import dev.kochki.rolliki.repository.VideoRepository;
import dev.kochki.rolliki.model.request.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final ProjectRepository projectRepository;
    private final VideoRepository videoRepository;
    private final MediaFileRepository mediaFileRepository;

    public SearchService(ProjectRepository projectRepository,
                         VideoRepository videoRepository,
                         MediaFileRepository mediaFileRepository) {
        this.projectRepository = projectRepository;
        this.videoRepository = videoRepository;
        this.mediaFileRepository = mediaFileRepository;
    }

    public List<Project> searchProjects(SearchRequest request) {
        if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            Pageable pageable = createPageable(request);
            Page<Project> page = projectRepository.findAll(pageable);
            return page.getContent();
        }

        // В реальном приложении здесь будет сложный поиск с полнотекстовым индексом
        // Пока простой поиск по имени и описанию
        String searchQuery = request.getQuery().toLowerCase();
        List<Project> allProjects = projectRepository.findAll();

        return allProjects.stream()
                .filter(project ->
                        project.getName().toLowerCase().contains(searchQuery) ||
                                (project.getDescription() != null &&
                                        project.getDescription().toLowerCase().contains(searchQuery)))
                .collect(Collectors.toList());
    }

    public List<Video> searchVideos(SearchRequest request) {
        if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            Pageable pageable = createPageable(request);
            Page<Video> page = videoRepository.findAll(pageable);
            return page.getContent();
        }

        String searchQuery = request.getQuery().toLowerCase();
        List<Video> allVideos = videoRepository.findAll();

        return allVideos.stream()
                .filter(video ->
                        video.getName().toLowerCase().contains(searchQuery) ||
                                (video.getDescription() != null &&
                                        video.getDescription().toLowerCase().contains(searchQuery)))
                .collect(Collectors.toList());
    }

    public List<MediaFile> searchMediaFiles(SearchRequest request) {
        if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            Pageable pageable = createPageable(request);
            Page<MediaFile> page = mediaFileRepository.findAll(pageable);
            return page.getContent();
        }

        String searchQuery = request.getQuery().toLowerCase();
        List<MediaFile> allMediaFiles = mediaFileRepository.findAll();

        return allMediaFiles.stream()
                .filter(mediaFile ->
                        mediaFile.getOriginalName().toLowerCase().contains(searchQuery) ||
                                (mediaFile.getDescription() != null &&
                                        mediaFile.getDescription().toLowerCase().contains(searchQuery)))
                .collect(Collectors.toList());
    }

    private Pageable createPageable(SearchRequest request) {
        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(request.getSortDirection()) ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                request.getSortBy()
        );
        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }
}