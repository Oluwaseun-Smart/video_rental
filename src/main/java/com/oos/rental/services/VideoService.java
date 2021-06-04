package com.oos.rental.services;

import com.oos.rental.entities.Video;
import com.oos.rental.models.Response;
import com.oos.rental.models.VideoType;
import com.oos.rental.repositories.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class VideoService {

    private VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Response save(Video video) {
        log.info(video.toString());
        if (Objects.nonNull(video.getVideoType()) && video.getVideoType().equals(VideoType.Children_Movie) && Objects.isNull(video.getMaximumAge()) && video.getMaximumAge() < 0) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_ALLOWED)
                    .setMessage("Maximum age field is required for Children’s Movie video type")
                    .build();
        }

        if (video.getVideoType().equals(VideoType.New_Release) && Objects.isNull(video.getYear())) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_ALLOWED)
                    .setMessage("Release year field is required for New Release video type")
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Video saved successfully", videoRepository.save(video));
    }

    public Response video(Long id) {
        final Video video = videoRepository.findById(id).orElse(null);
        if (Objects.isNull(video)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("Video does not exists")
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Video retrieved successfully", video);
    }

    public Response videos(Pageable pageable) {
        final Page<Video> allVideos = videoRepository.findAll(pageable);

        if (allVideos.isEmpty()) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("Video records does not exists")
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Video records retrieved successfully", allVideos);
    }


}
