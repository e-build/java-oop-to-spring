***목차***

### 프레임워크의 점진적인 개선


신규 서비스를 구축하는 작업 또한 쉽지 않지만, 누군가 개발해 놓은 애플리케이션에 지속적으로 기능을 추가하고 개선하면서 발전시켜 나가는 작업은 훨씬 더 어려운 일이다. 장기적인 관점에서 사용자의 요구에 빠르게 대응하고 좀 더 효율적으로 개발하기 위해 프레임워크 또는 새로운 기술을 적용해야 할 때가 온다. 그러나 사용자에게 추가적인 가치를 제공하지 못하면서 시간을 투자해야 한다는 것을 개발자가 아닌 다른 사람들에게 설득하는 일은 쉬운일이 아니다. 때론 여러 협업관계의 동료 개발자들에게 조차도 공감을 얻지 못할 수 도 있는 일이다. 이런 단점을 보완하는 방법으로 기존 방법과 새로운 방법으로 같이 서비스를 운영하면서 서비스를 중단하지 않고 점진적으로 새로운 기술로 변경해 가는 방법에 대해 공부해 볼 것이다.

이전에 구현한 MVC프레임워크 기반으로 동작하는 컨트롤러가 정상적으로 동작하는 상태에서 새로운 MVC 프레임워크로 점진적으로 전환해 가는 과정에 대해 살펴볼 것이다. 그 과정에서 애플리케이션을 객체지향적으로 설계하는 것이 왜 중요한지도 함께 알아보자

***개선된 MVC 프레임워크의 컨트롤러***

> 애노테이션을 활용해 설정을 추가하고 서버가 시작할 때 자동으로 요청 URL과 실행로직이 담겨있는 메서드가 매핑되도록 변경

```java
@Controller
public class HomeController{

	@RequestMapping("/")
	public String homePage(HttpRequest request, HttpResponse response){
		return "/home";
	}

	@RequestMapping("/user/login")
	public String loginPage(HttpRequest request, HttpResponse response){
		return "redirect:/user/login";
	}

}
```

### 요구사항

1. 컨트롤러가 추가될 때마다 `RequestMapping` 클래스에 요청과 컨트롤러를 추가해야 하는 불편함 개선
    1. reflections 라이브러리를 활용해 `@Controller` 애노테이션이 설정되어 있는 ***모든 클래스를 찾고***, 각 클래스에 대한 ***인스턴스 생성***을 담당하는 `ControllerScanner` 클래스 추가
    2. `AnnotationHandlerMapping` 클래스에서 애노테이션 기반 매핑 구현
        - `@Controller`클래스의 메서드 중 `@RequestMapping`이 설정되어 있는 메서드를 찾아서 `Map<HandlerKey, HandlerExecution>`에 각 요청정보와, 그에 연결되는 메서드 정보를 값으로 추가한다.
        - `HandlerExecution`은 자바 리플렉션에서 메서드를 실행하기 위해 필요한 정보를 가진다. 즉 실행할 메서드가 존재하는 클래스의 인스턴스 정보와 실행할 메서드 정보를 가져야 한다.

    ```java
    public class AnnotationHandlerMapping{

    	public void initialize(){
    		[...]
    	}

    	public HandlerExecution getHandler(HttpRequest request){
    		[...]	
    	};

    }
    ```

2. 새로운 기능이 추가될 때마다 매번 컨트롤러 클래스를 추가하는 것이 아니라, 메서드를 추가하는 방식으로 변경
3. 점진적으로 리팩토링이 가능한 구조로 개발

   > 1. 애노테이션 기반으로 새로운 MVC 프레임워크 구현
    2. 레거시 MVC 프레임워크와 새로운 MVC 프레임워크를 통합하는 방식으로 구현

    - RequestMapping, AnnotationHandlerMapping 두 클래스의 공통된 부분을 인터페이스로 추상화한다.

        ```java
        public interface HandlerMapping {
        	Object getHandler(HttpRequest request);
        }
        ```

    - 웹 서버 초기화과정에서 RequestMapping, AnnotaionHandlerMapping 모두 초기화 하고, 초기화한 2개의 HandlerMapping에서 요청 URL에 해당하는 컨트롤러를 찾아 메서드를 실행한다.

        ```java
        Object Handler = getHandler(req);
        if (handler instanceof Controller){
        	(Controller)handler.service(req, res);
        } else if (handler instanceof HandlerExecution){
        	(HandlerExecution)handler.handle(req, res);
        } else {
        	...
        }
        ```

    - 기존 컨트롤러를 새로 추가한 애노테이션 기반으로 설정한 후 정상적으로 동작하는 지 테스트한다. 테스트에 성공하면 기존의 컨트롤러를 새로운 MVC 프레임워크로 점진적으로 변경한다.