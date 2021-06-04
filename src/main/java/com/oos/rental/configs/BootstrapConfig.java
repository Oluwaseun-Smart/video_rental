package com.oos.rental.configs;

import com.oos.rental.entities.Price;
import com.oos.rental.entities.User;
import com.oos.rental.entities.Video;
import com.oos.rental.models.VideoGenre;
import com.oos.rental.models.VideoType;
import com.oos.rental.repositories.PriceRepository;
import com.oos.rental.repositories.UserRepository;
import com.oos.rental.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class BootstrapConfig implements CommandLineRunner {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Price> prices = new LinkedList<>();
        List<Video> videos = new LinkedList<>();
        List<User> users = new LinkedList<>();

        final Price regularPrice = Price.builder()
                .rate(10.00)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .videoType(VideoType.Regular).build();
        prices.add(regularPrice);

        final Price newReleasePrice = Price.builder()
                .rate(15.00)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .videoType(VideoType.New_Release).build();
        prices.add(newReleasePrice);

        final Price childrenMoviesPrice = Price.builder()
                .rate(8.00)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .videoType(VideoType.Children_Movie).build();
        prices.add(childrenMoviesPrice);

        if (!prices.isEmpty()) {
            priceRepository.saveAll(prices);
        }

        final Video regularVideo = Video.builder()
                .title("Godzilla vs. Kong")
                .videoType(VideoType.Regular)
                .videoGenre(VideoGenre.Action)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(regularVideo);

        final Video secondRegularVideo = Video.builder()
                .title("Wrath of Man")
                .videoType(VideoType.Regular)
                .videoGenre(VideoGenre.Drama)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(secondRegularVideo);

        final Video thirdRegularVideo = Video.builder()
                .title("Cruella")
                .videoType(VideoType.Regular)
                .videoGenre(VideoGenre.Romance)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(thirdRegularVideo);

        final Video childrenVideo = Video.builder()
                .title("Tom & Jerry")
                .videoType(VideoType.Children_Movie)
                .videoGenre(VideoGenre.Comedy)
                .maximumAge(15)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(childrenVideo);

        final Video newVideo = Video.builder()
                .title("Army of the Dead")
                .videoType(VideoType.New_Release)
                .videoGenre(VideoGenre.Horror)
                .year("2021")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(newVideo);

        final Video secondNewVideo = Video.builder()
                .title("Mortal Kombat")
                .videoType(VideoType.New_Release)
                .videoGenre(VideoGenre.Action)
                .year("2020")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        videos.add(secondNewVideo);

        if (!videos.isEmpty()) {
            videoRepository.saveAll(videos);
        }

        final User oluwaseunSmart = User.builder()
                .name("Oluwaseun Smart")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        users.add(oluwaseunSmart);

        final User lolaBena = User.builder()
                .name("Lola Bena")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        users.add(lolaBena);

        final User akinWunmi = User.builder()
                .name("Akin Wunmi")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();
        users.add(akinWunmi);

        if (!users.isEmpty()) {
            userRepository.saveAll(users);
        }
    }
}
