package com.example.demo;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
public class Result <T>{
    String message;
    T data;
}
