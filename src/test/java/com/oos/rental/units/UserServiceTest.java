package com.oos.rental.units;

import com.oos.rental.entities.User;
import com.oos.rental.models.Response;
import com.oos.rental.repositories.UserRepository;
import com.oos.rental.services.UserService;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static List<User> users = null;
    private static Page<User> userPage = null;

    @BeforeAll
    static void setup() {
        users = new LinkedList<>();

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

        userPage = new PageImpl<>(users);
    }

    @Test
    void testAllUsers() {

        int page = 0;
        int size = 20;
        Pageable pageable = PageRequest.of(page, size);

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        final Response usersResponse = userService.users(pageable);

        final Page<User> userPage = (Page<User>) usersResponse.getData();

        assertEquals(100, usersResponse.getCode());
        assertEquals(true, usersResponse.getStatus());
        assertEquals("Users records retrieved successfully", usersResponse.getMessage());

        assertEquals(100, usersResponse.getCode());
        assertEquals(3, userPage.getSize());
        assertEquals(3, userPage.getTotalElements());
        assertEquals(true, userPage.isLast());
        assertEquals(true, userPage.isFirst());

    }

    @Test
    void testCreateUser() {
        final User oluwaseunSmart = User.builder()
                .name("Oluwaseun Smart")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();

        when(userRepository.save(oluwaseunSmart)).thenReturn(oluwaseunSmart);

        final Response userResponse = userService.create(oluwaseunSmart);
        final User newUser = (User) userResponse.getData();

        assertEquals("Oluwaseun Smart", newUser.getName());

        assertEquals(100, userResponse.getCode());
        assertEquals(true, userResponse.getStatus());
        assertEquals("User saved successfully", userResponse.getMessage());
    }

    @Test
    void testGetUserById() {
        final User oluwaseunSmart = User.builder()
                .name("Oluwaseun Smart")
                .createdAt(LocalDate.now())
                .updateAt(LocalDate.now())
                .build();

        final Optional<User> optionalUser = Optional.of(oluwaseunSmart);

        when(userRepository.findById(1L)).thenReturn(optionalUser);

        final Response userResponse = userService.user(1L);
        final User user = (User) userResponse.getData();

        assertEquals(100, userResponse.getCode());
        assertEquals(true, userResponse.getStatus());
        assertEquals("User record retrieved successfully", userResponse.getMessage());
        assertEquals("Oluwaseun Smart", user.getName());
    }
}
