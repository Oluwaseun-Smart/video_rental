package com.oos.rental.units;

import com.oos.rental.entities.Price;
import com.oos.rental.entities.User;
import com.oos.rental.entities.Video;
import com.oos.rental.models.Response;
import com.oos.rental.models.VideoGenre;
import com.oos.rental.models.VideoType;
import com.oos.rental.repositories.PriceRepository;
import com.oos.rental.repositories.UserRepository;
import com.oos.rental.repositories.VideoRepository;
import com.oos.rental.services.PriceService;
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
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    private static List<Price> prices = null;
    private static Page<Price> pricePage = null;


    @BeforeAll
    static void setup() {
        prices = new LinkedList<>();

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

        pricePage = new PageImpl<>(prices);
    }

    @Test
    void testAllPrice() {
        int page = 0;
        int size = 20;
        Pageable pageable = PageRequest.of(page, size);

        when(priceRepository.findAll(pageable)).thenReturn(pricePage);
        final Response testPriceResponse = priceService.prices(pageable);

        final Page<Price> pricePage = (Page<Price>) testPriceResponse.getData();
        assertEquals(100, testPriceResponse.getCode());
        assertEquals(true, testPriceResponse.getStatus());
        assertEquals("Price records retrieved successfully", testPriceResponse.getMessage());

        assertEquals(3, pricePage.getSize());
        assertEquals(3, pricePage.getTotalElements());
        assertEquals(true, pricePage.isLast());
        assertEquals(true, pricePage.isFirst());
        assertEquals(false, pricePage.isEmpty());
    }

    @Test
    void testCreatePrice() {
        final Price price = Price.builder()
                .rate(10.00)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .videoType(VideoType.Regular).build();

        when(priceRepository.save(price)).thenReturn(price);

        final Response testPriceResponse = priceService.save(price);
        final Price newPrice = (Price) testPriceResponse.getData();

        assertEquals(100, testPriceResponse.getCode());
        assertEquals(true, testPriceResponse.getStatus());
        assertEquals("Price saved successfully", testPriceResponse.getMessage());

        assertEquals(10.00, newPrice.getRate());
        assertEquals(VideoType.Regular, newPrice.getVideoType());
    }

    @Test
    void testGetPriceById() {
        final Price price = Price.builder()
                .rate(10.00)
                .id(1L)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .videoType(VideoType.Regular).build();

        final Optional<Price> optionalPrice = Optional.of(price);

        when(priceRepository.findById(1L)).thenReturn(optionalPrice);

        final Response testPriceResponse = priceService.price(1L);
        final Price priceData = (Price) testPriceResponse.getData();

        assertEquals(100, testPriceResponse.getCode());
        assertEquals(true, testPriceResponse.getStatus());
        assertEquals("Price retrieved successfully", testPriceResponse.getMessage());

        assertEquals(10.00, priceData.getRate());
        assertEquals(VideoType.Regular, priceData.getVideoType());
    }

    @Test
    void testVideoRentalFeesCalculation() {
        final Price price = Price.builder()
                .rate(8.00)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .videoType(VideoType.Children_Movie).build();

        final User oluwaseunSmart = User.builder()
                .id(1L)
                .name("Oluwaseun Smart")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();

        final Video video = Video.builder()
                .id(1L)
                .title("Tom & Jerry")
                .videoType(VideoType.Children_Movie)
                .videoGenre(VideoGenre.Comedy)
                .maximumAge(15)
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();

        when(priceRepository.findByVideoType(VideoType.Children_Movie)).thenReturn(Optional.of(price));
        when(userRepository.findById(1L)).thenReturn(Optional.of(oluwaseunSmart));
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        final Response rentalFeeResponse = priceService.calculateVideoRentalFee(1L, 1L, 2);

        assertEquals(100, rentalFeeResponse.getCode());
        assertEquals(true, rentalFeeResponse.getStatus());
        assertEquals("Hello Oluwaseun Smart, Tom & Jerry rental fee for 2 days is 23.00 Birr", rentalFeeResponse.getMessage());
    }
}
