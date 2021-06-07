package com.oos.rental.units;

import com.oos.rental.entities.Video;
import com.oos.rental.models.Response;
import com.oos.rental.models.VideoGenre;
import com.oos.rental.models.VideoType;
import com.oos.rental.repositories.VideoRepository;
import com.oos.rental.services.VideoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    private static List<Video> videos = null;
    private static Page<Video> videoPage = null;

    @BeforeAll
    static void setup() {
        videos = new LinkedList<>();

        final Video regularVideo = Video.builder()
                .title("Godzilla vs. Kong")
                .videoType(VideoType.Regular.getType())
                .videoGenre(VideoGenre.Action.getGenre())
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(regularVideo);

        final Video secondRegularVideo = Video.builder()
                .title("Wrath of Man")
                .videoType(VideoType.Regular.getType())
                .videoGenre(VideoGenre.Drama.getGenre())
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(secondRegularVideo);

        final Video thirdRegularVideo = Video.builder()
                .title("Cruella")
                .videoType(VideoType.Regular.getType())
                .videoGenre(VideoGenre.Romance.getGenre())
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(thirdRegularVideo);

        final Video childrenVideo = Video.builder()
                .title("Tom & Jerry")
                .videoType(VideoType.Children_Movie.getType())
                .videoGenre(VideoGenre.Comedy.getGenre())
                .maximumAge(15)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(childrenVideo);

        final Video newVideo = Video.builder()
                .title("Army of the Dead")
                .videoType(VideoType.New_Release.getType())
                .videoGenre(VideoGenre.Horror.getGenre())
                .year("2021")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(newVideo);

        final Video secondNewVideo = Video.builder()
                .title("Mortal Kombat")
                .videoType(VideoType.New_Release.getType())
                .videoGenre(VideoGenre.Action.getGenre())
                .year("2020")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(secondNewVideo);

        videoPage = new PageImpl<>(videos);
    }

    @Test
    void testAllVideos() {

        int page = 0;
        int size = 20;
        Pageable pageable = PageRequest.of(page, size);

        when(videoRepository.findAll(pageable)).thenReturn(videoPage);
        final Response videoResponse = videoService.videos(pageable);

        final Page<Video> videoPage = (Page<Video>) videoResponse.getData();
        assertEquals(100, videoResponse.getCode());
        assertEquals(true, videoResponse.getStatus());
        assertEquals("Video records retrieved successfully", videoResponse.getMessage());

        assertEquals(6, videoPage.getSize());
        assertEquals(6, videoPage.getTotalElements());
        assertEquals(true, videoPage.isLast());
        assertEquals(true, videoPage.isFirst());
        assertEquals(false, videoPage.isEmpty());
    }

    @Test
    void testCreateVideo() {
        final Video video = Video.builder()
                .title("Army of the Dead")
                .videoType(VideoType.New_Release.getType())
                .videoGenre(VideoGenre.Horror.getGenre())
                .year("2021")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();

        when(videoRepository.save(video)).thenReturn(video);

        final Response videoResponse = videoService.save(video);
        final Video newVideo = (Video) videoResponse.getData();

        assertEquals(100, videoResponse.getCode());
        assertEquals(true, videoResponse.getStatus());
        assertEquals("Video saved successfully", videoResponse.getMessage());

        assertEquals("Army of the Dead", newVideo.getTitle());
        assertEquals(VideoType.New_Release.getType(), newVideo.getVideoType());
        assertEquals(VideoGenre.Horror.getGenre(), newVideo.getVideoGenre());
        assertEquals("2021", newVideo.getYear());
    }

    @Test
    void testGetVideoById() {
        final Video video = Video.builder()
                .title("Army of the Dead")
                .videoType(VideoType.New_Release.getType())
                .videoGenre(VideoGenre.Horror.getGenre())
                .year("2021")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();

        final Optional<Video> optionalVideo = Optional.of(video);

        when(videoRepository.findById(1L)).thenReturn(optionalVideo);

        final Response testVideo = videoService.video(1L);
        final Video videoData = (Video) testVideo.getData();

        assertEquals(100, testVideo.getCode());
        assertEquals(true, testVideo.getStatus());
        assertEquals("Video retrieved successfully", testVideo.getMessage());

        assertEquals("Army of the Dead", videoData.getTitle());
        assertEquals(VideoGenre.Horror.getGenre(), videoData.getVideoGenre());
        assertEquals(VideoType.New_Release.getType(), videoData.getVideoType());
        assertEquals("2021", videoData.getYear());
    }
}
