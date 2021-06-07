package com.oos.rental.controllers;


import com.oos.rental.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/v1/")
public class IndexController {

    public IndexController() {
    }

    @GetMapping({"", "/index", "/"})
    public ResponseEntity<Response> getIndex() {
        final Response response = new Response.Builder<>()
                .buildSuccess("WELCOME TO VIDEO RENTAL APP");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
