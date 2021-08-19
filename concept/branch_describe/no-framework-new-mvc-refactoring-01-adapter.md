
# 한계
no-framework-new-mvc 브랜치를 다시 살펴보며 어떤 한계점이 있을 지 함께 고민해보자. 애노테이션 기반의 새로운 MVC 프레임워크를 추가하기 위해
서버가 구동될 때 `AnnotationHandlerMapping` 클래스에서 `@RequestMapping`애노테이션이 붙어있는 메서드들을 찾아 HTTP URL과 METHOD에 매핑시키는
초기화 작업을 실시한다. 이전에는 URL, METHOD를 컨트롤러에 매핑시키기 위해 `RequestMapping` 클래스에서 직접 HTTP 요청에 매핑되는 컨트롤러를 입력하여 매핑시키던 것을, 
이제는 메서드 수준에서의 애노테이션 선언만하면 매핑된 handler를 추가할 수 있다는 점은 상당한 편리함을 제공한다.

이렇듯 기존의 프레임워크에 새로운 프레임워크를 도입하는 것은 큰 이점이 있다. 따라서 새로운 방식을 도입하는 것에 열려있는 설계가 될 수 있도록, 현재 자신이 사용하는 프레임워크가 
충분히 확장성있는 설계인지 고민하는 습관은 급격한 비즈니스 변화속에서 큰 도움이 되는 역량일 것이다. 그렇다면 `no-framework-new-mvc` 브랜치는 또 다른 방식의 MVC 요청을 처리하는 프레임워크를 도입하고자 할 때 충분히 확장성있는 설계일까?

다음`DispatchRequest` 클래스의 run() 메서드를 살펴보며 `LegacyHandlerMapping`과 `AnnotationHandlerMapping` 이라고 하는 
서로 다른 사용방식을 가진 MVC 프레임워크가 합쳐지는 과정에 어떤 한계점이 존재하는 지 고민해보자.
```java
Object handler = getHandler(request);
if (handler == null) {
    [...]
} else {
    if ( handler instanceof Controller)
        ((Controller) handler).service(request, response);
    else
        ((HandlerExecution) handler).handle(request, response);
}
```

getHandler()를 통해 handler를 가져온 이후 instanceof를 통해 타입을 찾고, 그에 해당하는 타입으로 캐스팅하여 각각 메서드를 실행하는 것을 볼 수 있다.
하지만 매번 새로운 방식의 프레임워크나 라이브러리를 추가하려 할 때 마다 이처럼 분기문을 통해 타입을 찾고, 캐스팅하는 방식은 
기존 코드를 수정해야 한다는 점에서 객체지향 설계 5원칙 중 [OCP](https://github.com/e-build/java-oop-to-spring/blob/main/concept/oop-5-principle.md#ocpopen-closed-principle) 원칙에 어긋나며,
무엇보다 프로젝트 내에 모든 코드를 수정할 수 있는 권한이 있는 경우와 달리 수정불가능한 외부 라이브러리나 프레임워크를 기반으로 개발된 컨트롤러들을 통합하는 것은 아예 불가능하다는 큰 단점이 존재한다.

우리는 서로 다른 방식의 MVC 프레임워크인 `LegacyHandlerMapping`과 `AnnotationHandlerMapping`을 통합하기 위해 다음과 같은 `HandlerMapping`이라는 인터페이스를 활용했다.
```java
public interface HandlerMapping {
    
    Object getHandler(HttpRequest request);
}
```
만약 새로 추가되는 프레임워크를 우리가 직접 수정할 수 있다면, 또 다시 `HandlerMapping`을 활용하여 통합하는 것이 가능은 할 것이다. 다음은 스트럿츠라고 하는 
자바 진영의 대표적인 MVC 프레임워크의 컨트롤러 인터페이스이다.  
```java
public interface Action{
    
    public String execute() throws Exception;
}
```
스트럿츠 프레임워크를 통해 개발된 `Action` 인터페이스를 구현하는 컨트롤러들이 있다고 했을 때, 우리는 `Action`이라는 인터페이스를 직접 수정할 방법이 없기 때문에 통합하는 것이 불가능해지는 것이다. 
따라서 좀 더 유연한 구조를 지원하도록 하기 위해서는 인터페이스 하나로 강제하는 것은 바람직하지 않다.   

 

# 리팩토링
1. 새로운 유형의 컨트롤러가 추가되더라도 서로간의 영향을 주지않으면서 확장할 수 있는 방법을 찾아본다.
   서로 다른 인터페이스를 인터페이스 하나로 연결한느 방법을 찾아야 한다. 여러개의 프레임워크 컨트롤러를 하나로 통합해야 하는 상황이며,
   각 프레임워크들의 컨트롤러가 수행하는 역할은 'HTTP 요청 처리를 수행한다.'로 동일하다. 즉, 또 하나의 추상화 단계가 필요하다는 의미이다.