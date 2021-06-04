package com.oos.rental.services;

import com.oos.rental.entities.Price;
import com.oos.rental.entities.User;
import com.oos.rental.entities.Video;
import com.oos.rental.models.Response;
import com.oos.rental.models.VideoType;
import com.oos.rental.repositories.PriceRepository;
import com.oos.rental.repositories.UserRepository;
import com.oos.rental.repositories.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PriceService {

    private PriceRepository priceRepository;
    private UserRepository userRepository;
    private VideoRepository videoRepository;

    public PriceService(PriceRepository priceRepository,
                        UserRepository userRepository,
                        VideoRepository videoRepository) {

        this.priceRepository = priceRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    public Response save(Price price) {
        final Price oldVideoTypePrice = priceRepository.findByVideoType(price.getVideoType()).orElse(null);

        if (Objects.nonNull(oldVideoTypePrice) && Objects.nonNull(price.getId())) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_ALLOWED)
                    .setMessage(String.format("Price has been created for %", price.getVideoType().getType()))
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Price saved successfully", priceRepository.save(price));
    }

    public Response price(Long id) {
        final Price price = priceRepository.findById(id).orElse(null);

        if (Objects.isNull(price)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("Price does not exists")
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Price retrieved successfully", price);
    }

    public Response prices(Pageable pageable) {
        final Page<Price> allPrices = priceRepository.findAll(pageable);

        if (Objects.isNull(allPrices) || allPrices.isEmpty()) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("Price records does not exists")
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Price records retrieved successfully", allPrices);
    }

    public Response calculateVideoRentalFee(Long userId, Long videoId, Integer days) {
        Double videoRentalFee = 0.0;
        String year = null;

        final User user = userRepository.findById(userId).orElse(null);
        if (Objects.isNull(user)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("User does not exists")
                    .build();
        }
        String userName = user.getName();

        final Video video = videoRepository.findById(videoId).orElse(null);
        if (Objects.isNull(video)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("Video does not exists")
                    .build();
        }

        final VideoType videoType = video.getVideoType();

        final Price price = priceRepository.findByVideoType(videoType).orElse(null);
        if (Objects.isNull(price)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("Price does not exists")
                    .build();
        }

        final Integer maximumAge = video.getMaximumAge();

        if (price.getVideoType().equals(VideoType.New_Release))
            year = video.getYear().length() > 2 ? video.getYear().substring(video.getYear().length() - 2) : video.getYear();

        Double rate = price.getRate();
        final String videoTitle = video.getTitle();


        if (price.getVideoType().equals(VideoType.Regular))
            videoRentalFee = rate * days;

        if (price.getVideoType().equals(VideoType.Children_Movie))
            videoRentalFee = rate * days + maximumAge / 2;

        if (price.getVideoType().equals(VideoType.New_Release))
            videoRentalFee = rate * days - Integer.parseInt(year);


        return new Response.Builder<>()
                .buildSuccess(String.format("Hello %s, %s rental fee for %d days is %.2f Birr", userName, videoTitle, days, videoRentalFee));

    }
}
