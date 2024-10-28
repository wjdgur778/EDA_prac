package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;


    public String create(RequestCreateDto requestCreateDto){
        // 사용자 생성 로직 (DB 저장 등)
        User user= User.builder()
                        .email(requestCreateDto.getEmail())
                .build();
        userRepository.save(user);

        // UserCreatedEvent 발행
        UserCreatedEvent event = new UserCreatedEvent(requestCreateDto.getEmail());

        eventPublisher.publishEvent(event);
        return "created";
    }
}
