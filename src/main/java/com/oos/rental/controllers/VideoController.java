package com.oos.rental.controllers;

import com.oos.rental.entities.Price;
import com.oos.rental.entities.Video;
import com.oos.rental.models.Response;
import com.oos.rental.services.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/videos/")
@Slf4j
public class VideoController {

    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping("create")
    public ResponseEntity<Response> createVideo(@RequestBody Video video) {
        final Response response = videoService.save(video);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> video(@PathVariable Long id) {
        final Response response = videoService.video(id);
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> videos(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        final Response response = videoService.videos(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        if (!response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
