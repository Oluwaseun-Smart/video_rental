package com.oos.rental.services;

import com.oos.rental.entities.User;
import com.oos.rental.models.Response;
import com.oos.rental.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response create(User user) {
        if (Objects.isNull(user.getName())) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("User name is required")
                    .build();
        }

        return new Response.Builder<>()
                .buildSuccess("User saved successfully", userRepository.save(user));
    }

    public Response user(Long id) {
        if (Objects.isNull(id)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("User Id is required")
                    .build();
        }
        final User user = userRepository.findById(id).orElse(null);
        if (Objects.isNull(user)) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("User does not exists")
                    .build();
        }

        return new Response.Builder<>()
                .buildSuccess("User record retrieved successfully", user);
    }

    public Response users(Pageable pageable) {
        final Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            return new Response.Builder<>()
                    .setStatus(false)
                    .setCode(Response.Code.NOT_FOUND)
                    .setMessage("No user found")
                    .build();
        }
        return new Response.Builder<>()
                .buildSuccess("Users records retrieved successfully", users);
    }
}
