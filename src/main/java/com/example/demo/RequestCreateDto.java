package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class RequestCreateDto {
    String email;
    String message;

    public RequestCreateDto(String email,String message){
        this.email = email;
        this.message = message;
    }
}
