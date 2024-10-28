### 이벤트 기반 아키텍처 공부
🔥이 글의 목적은 확장성과 유연성에 좋은 이벤트 기반 아키텍쳐 체험하고 공부함에 있다.

#### 이벤트 기반 아키텍쳐란?
이벤트 기반 아키텍처(Event-Driven-Architecture, EDA)는 소프트웨어 아키텍처의 한 패턴으로, 분산된 서비스들이 이벤트를 기반으로 통신하는 구조입니다.

여기서 이벤트는 상태변화(ex:상품가격, 배송 주소 등)을 전달할 수 있으며, 이벤트가 식별자(ex: 주문 접수, 배송알림 등)일 수 있습니다.

이벤트를 게시하는 생성자와, 이벤트를 처리하는 독립적으로 동작할 수 있습니다. 이를 통해 서로 간의 **느슨한 결합**을 이뤄내어 **확장성과 유연성**이 향상됩니다.

#### 이벤트 기반 아키텍쳐의 장점
첫째, 각 컴포넌트는 이벤트 메세지를 통해서만 통신을 하기때문에 시스템 간의 결합도를 낮출 수 있다.

둘째, 비동기 처리(@Async)를 통해 성능을 최적화 할 수 있습니다. 비교적 덜 중요한 동작을 비동기로 처리해 효율성을 높일 수 있습니다.

셋째, 확장성과 재사용성이 향상됩니다. 이벤트를 중심으로 모듈화 되어 있기때문에 추가 수정에 유리합니다. 

#### 이벤트 기반 아키텍쳐의 단점
첫째, 디버깅과 테스트에 불리할 수 있습니다. 비동기적이고 분산된 시스템에서는 문제의 원인을 찾기 힘들고 가능한 이벤트의 조합을 테스트 하기도 힘들기 때문입니다.

둘째, 데이터 일관성 유지에 불리할 수 있습니다. 이벤트 기반 시스템에서는 데이터가 여러 서비스에 걸쳐 분산되어 있을 수 있기 때문입니다.

셋째, 다수의 서비스가 비동기적으로 처리된다면 이벤트 처리 순서를 보장하지 못할 수 있습니다.


#### 시나리오

사용자가 서비스에 가입을 할때 (welcome email)환영 이메일을 함께 보낸다.
이 시나리오는 간단하게 이벤트를 생성하고 처리해보는 방식이다.
단일 애플리케이션 내에서만 사용하는 ```ApplicationEventPublisher```를 사용한 이벤트 기반 아키텍쳐

실제로는 단일 애플리케이션으로만 동작하는 서비스는 없을 것이다. 

```kafka```등을 사용하여 여러 서비스가 독립적으로 운영되는 마이크로서비스 아키텍처에서 이벤트를 공유하고 통신할 것이라 생각된다.

#### 구현에 사용한 코드
```java
// springboot 애플리케이션 내에서의 이벤트를 전달하고 처리
private final ApplicationEventPublisher eventPublisher;
```

```java 
// <UsrService>

// UserCreatedEvent 발행
// UserCreatedEvent는 email를 필드로 갖고있는 POJO 형태
// 이벤트 발생시 필요한 최소한의 정보를 포함한다.
UserCreatedEvent event = new UserCreatedEvent(requestCreateDto.getEmail());
eventPublisher.publishEvent(event);
```


```ApplicationEventpublisher```의 인스턴스가 이벤트를 발행하면 귀를 기울이고 있는 리스너가 반응한다.


```java
@Component
@RequiredArgsConstructor
public class UserEventListener {
    private final JavaMailSender mailSender;

    // @EventListner는 파라미터로 받는 이벤트의 타입에 따라 호출된다.
    // 이벤트의 타입이 동일할 경우 순서가 보장되지 않으므로 
    // @Order()를 통해 순서를 제어한다.
    
    @EventListener
    @Order(1)
    public void sendWelcomeEmail(UserCreatedEvent event) {
        System.out.println("fisrt listener");
        // 이메일 전송 로직
    }
    @EventListener
    @Order(2)
    public void secondLogic(UserCreatedEvent event) {
        System.out.println("second listener");
        // 두번째 곁가지 로직
    }
}
```
범용적으로 **@EventListener**를 사용하지만 이밖에 **@TransactionalEventListener**를 사용할 수도 있다. **AFTER_COMMIT**, **BEFORE_COMMIT**등 과 같은 설정을 통해 
이벤트를 발행했던 트랜젝션 사이 원하는 순간에 실행할 수 있게 할 수 있습니다.