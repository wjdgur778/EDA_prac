package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Result> createUser(@RequestBody RequestCreateDto requestCreateDto) {
        System.out.println(requestCreateDto.getEmail());
        userService.create(requestCreateDto);
        return ResponseEntity.status(200).body(Result.builder()
                .message("hi")
                .data(null)
                .build()
        );
    }

}
