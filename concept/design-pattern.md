***목차***

- [Adapter](#adapter)
- [Proxy](#proxy)
- [Decorator](#decorator)
- [Singleton](#singleton)
- [Template Method](#template-method)
- [Factory Method](#factory-method)
- [Strategy](#strategy)
- [Template Callback](#template-callback)

디자인 패턴은 실제 개발현장에서 비즈니스 요구 사항을 프로그래밍으로 처리하면서 만들어진 다양한 해결책 중에서 많은 사람들이
인정한 베스트 프랙티스를 정리한 것이다. 디자인 패턴은 객체지향의 특성과 설계원칙을 기반으로 구현되어 있다. 

스프링은 객체 지향의 특성과 설계 원칙을 극한까지 적용한 프레임워크이기에 스프링을 공부하다 보면
자연스럽게 객체 지향 설계의 베스트 프랙티스, 즉 디자인 패턴을 만날 수 있다.

디자인 패턴은 객체 지향의 특성 중 상속(extends), 인터페이스(implements), 합성(객체를 속성으로 사용)을 이용한다. 이 세 가지가 전부이다. 따라서 
여러 디자인 패턴이 비슷해보일 수 있으니 집중해서 살펴보자.

# Adapter 
어댑터 패턴의 역할은 서로 다른 두 인터페이스 사이에 통신이 가능하게 하는 것이다. 이전에도 살펴봤듯 다양한 데이터베이스 시스템을 공통의 인터페이스를 이용해 
조작하도록 하는 JDBC가 어댑터 패턴을 이용하는 좋은 사례이다. JDBC는 객체지향 설계원칙 중 OCP 를 설명할 때 예로 들었던 내용인데, 결국 어댑터 패턴은
개방 폐쇄 원칙을 활용한 설계 패턴이라고 할 수 있다.
```java
public class ServiceA{
    
    void runServiceA(){
        // ...
    }
}
```
```java
public class ServiceB{
    
    void runServiceB(){
        // ...
    }
}
```
```java
public class AdapterServiceA{
    ServiceA serviceA = new ServiceA();
    
    void runService(){
        serviceA.runServiceA();
    }
}
```
```java
public class AdapterServiceB{
    ServiceB serviceB = new ServiceB();
    
    void runService(){
        serviceB.runServiceB();
    }
}
```
```java
public class MainClass{
    
    public static void main(String[] args){
        ServiceA serviceA = new ServiceA();
        ServiceB serviceB = new ServiceB();

        // 각기 다른 메서드명을 호출
        serviceA.runServiceA(); 
        serviceB.runServiceB();
        
        // 어댑터 적용
        AdapterServiceA serviceA = new AdapterServcieA();
        AdapterServiceB serviceB = new AdapterServcieB();

        // 동일한 메서드 호출
        serviceA.runService();
        serviceB.runService();
    }
}
```
어댑터를 사용하는 쪽에서는 어댑터를 이용해 `service()`라는 동일한 메서드명으로 두 객체의 메서드를 호출하는 것을 볼 수 있다.
어댑터들이 인터페이스를 구현하게 해서 좀 더 깔끔하게 리팩토링 할 수 있는 여지가 남아있지만, 다른 패턴들과의 뚜렷한 비교를 위해 이 정도로 예시를 들었다.
즉, 위의 예와 같이 어댑터 패턴은 합성을 사용하는 디자인패턴이며 ***호출당하는 쪽의 메서드를 호출하는 쪽의 코드에 대응하도록 중간에 변환기를 통해 호출하는 패턴***인 것이다. 

# Proxy
프록시는 대리자라는 의미의 단어이다. 대리자라는 단어를 통해 어느 정도 유추해볼 수 있겠지만, 프록시 패턴이란 본래 호출되던 특정 로직을 대리자로 한번 감싸고 대리자를 통해 호출하도록 하는 것이다.
```java
public interface IService{
    void runSomething();
}
```
```java
public class Service implements IService{
    @Override
    void runSomething(){
        // ...
    }
}
```
```java
public class Proxy implements IService{
    Service service = new Service();
    @Override
    void runSomething(){
        
        // service.runSomething() 전 실행할 동작
        
        service.runSomethig();
        
        // service.runSomething() 후 실행할 동작
    }
}
```
Proxy클래스르 보면 Service 와 같은 인터페이스를 구현하면서 Service 객체를 멤버 속성으로 두고 있다. 또한 프록시 패턴은 실제 서비스 메서드에 반환값을 수정하거나 
인자를 커스텀하여 전달하기 보단 ***제어의 흐름을 변경하거나 다른 로직을 수행할 수 있도록 하는 설계 패턴이다***. 다음과 같은 프록시 패턴의 특징을 기억하자.
* Proxy는 실제 서비스와 같은 이름의 메서드를 구현한다. 이 때 인터페이스를 사용한다.
* Proxy는 실제 서비스의에 대한 참조 변수를 갖는다(합성). 
* Proxy는 실제 서비스의 같은 이름을 가진 메서드를 호출하고 그 값을 클라이언트에게 돌려준다.
* Proxy는 실제 서비스의 메서드 호출 전후에 별도의 로직을 수행할 수도 있다.
# Decorator
```java
public interface IService{
    void runSomething();
}
```
```java
public class Service implements IService{
    @Override
    void runSomething(){
        // ...
    }
}
```
```java
public class Decorator implements IService{
    Service service = new Service();
    String decoration = "***";
    @Override
    String runSomething(){
        
        // service.runSomething() 전 실행할 동작
        return decoration + service.runSomethig();
    }
}
```
반환값에 "***"라고 하는 장식을 더하는 것 빼면 프록시 패턴과 동일하다. 
* Decorator는 실제 서비스와 같은 이름의 메서드를 구현한다. 이때 인터페이스를 사용한다. 
* Decorator는 실제 서비스에 대한 참조 변수를 갖는다(합성). 
* Decorator는 실제 서비스와 같은 이름의 메서드를 호출하고, 그 반환값에 장식을 더해 클라이언트에게 돌려준다.
* Decorator는 실제 서비스의 메서드 호출 전후에 별도의 로직을 수행할 수도 있다.

즉, ***실제 서비스의 반환 값을 포장하는 패턴이 데코레이터 패턴***을 기억하자.

# Singleton
싱글턴 패턴이란 인스턴스를 하나만 만들어 사용하기 위한 패턴이다. 커넥션 풀, 스레드 풀, 디바이스 설정 객체 등과 같은 경우 
인스턴스를 여러개 만들게 되면 불필요한 자원을 사용하게 되고, 또 프로그램이 예상치 못한 결과를 낳을 수 있다. 싱글턴 패턴은 오직 인스턴스를 하나만 만들고 그것을 계속해서 재사용한다.
싱글턴 패턴을 적용하기 위해선 다음 세가지가 요구된다.

* new 연산자를 사용할 수 없도록 생성자에 private 접근제어자를 지정한다.
* 유일한 단일 객체를 반환할 수 있는 정적 메서드가 필요하다.
* 유일한 단일 객체를 참조할 정적 참조 변수가 필요하다.

<<--예시 추가 예-->>

# Template Method
말 그대로 메서드의 템플릿을 만들고 상속받는 하위클래스에서 템플릿 중 일부 메서드를 오버라이드하여 사용할 수 있도록 하는 설계 패턴이다. 다음의 예를 보자
```java
public class Man{
    public void play {
        컴퓨터를켠다();
        게임한다();
        컴퓨터를종료한다();
    }
}
```
```java
public class Women{
    public void play {
        컴퓨터를켠다();
        유튜브를본다();
        컴퓨터를종료한다();
    }
}
```
남자와 여자 클래스 모두 `play()` 메서드를 호출하면 컴퓨터를 키고 끄는 동작은 동일한데 중간에 게임을 하느냐 유튜브를 보느냐의 차이가 존재한다. 
중복된 로직이 존재하기 떄문에 이를 따로 분할할 필요가 있어보인다. 중복을 제거하며 요구에 따라 하위클래스가 필요한 로직을 작성하도록 메서드를 제공해보자.   

```java
public abstract class Human{
    // 템플릿메서드
    public void play(){
        컴퓨터를켠다();
        doContents();
        컴퓨터를종료한다();
    }

    // 추상 메서드
    abstract void doContents();
    
    // Hook 메서드
    public void 컴퓨터를켠다(){
        // ...
    }
    // Hook 메서드
    public void 컴퓨터를끈다(){
        // ...
    }
}
```
```java
public class Man extends Human{
    @Override
    public void doContents() {
        System.out.println("게임한다");
    }
    @Override
    public void 컴퓨터를끈다() {
        System.out.println("절전모드로 변경");
    }
}
```
```java
public class Women extends Human{
    @Override
    public void doContents() {
        System.out.println("유튜브를 본다.");
    }
}
```

```java
public class MainClass{
    public static void main(String[] args) {
        Human women = new Women();
        Human man = new Man();
        
        women.play(); // 컴퓨터를 켠다 => 유튜브를 본다 => 컴퓨터를 끈다. 
        man.play(); // 컴퓨터를 켠다 => 게임한다 => 컴퓨터를 절전모드로 변경한다.
    }
}
```
위와 같이 Human 추상클래스에서 `play()`라고 하는 메서드의 템플릿을 제공함으로써 남자와 여자 클래스가 `play()`를 실행 할 때 요구되는 공통 로직을 정의하고, 
각기 다르게 동작하는 부분은 `doContents()` 라고 하는 추상메서드를 오버라이딩함으로써 구현하도록 하였다. 남자클래스는 추가적으로 컴퓨터를 끄는 메서드를 오버라이딩하여 
컴퓨터를 끄지않고 절전모드로 변경하게 하였는데, 이처럼 상위클래스에서 이미 구현까지 되어있으며 하위메서드에서 선택적으로 구현하도록 하는 메서드를 Hook이라고 한다.

즉, ***템플릿 메서드는 상위 클래스의 견본 메서드에서 하위 클래스가 오버라이딩한 메서드를 호출하는 패턴***이라고 할 수 있다.

# Factory Method
팩토리는 흔히 알다시피 공장을 의미한다. 공장은 일반적으로 물건을 생산하는데, 객체지향에서 팩터리는 객체를 생성한다.
> 오버라이드된 메서드가 객체를 반환하는 패턴

# Strategy
전략패턴은 디자인 패턴 중에서도 아주 핵심적인 패턴이다. 전략 패턴을 구성하는 세 가지 요소는 다음과 같다.
* 전략 메서드를 가진 전략 객체
* 전략 객체를 사용하는 컨텍스트(전략 객체의 사용자)
* 전략 객체를 생성해 컨텍스트에 주입하는 클라이언트(전략 객체의 공급자)

클라이언트는 다양한 전략 중 하나를 선택해 생성한 후 컨텍스트에 주입한다.
 
초등학생 아이가 엄마가 준 장난감을 가지고 놀고 있다고 생각해보자. 아이는 엄마가 준 장난감에 따라 각기 다른 놀이를 수행한다. 여기서 장난감은 전략이 되고
엄마는 클라이언트, 아이는 컨텍스트라고 할 수 있다. 

```java
public interface ToyStrategy{
    public abstract void play();
}
```
```java
public class ToyBibi implements ToyStrategy{
    @Override
    public void play(){
        System.out.println("비비의 팔다리를 이렇게~ 저렇게~");
    }
}
```
```java
public class ToyTank implements ToyStrategy{
    @Override
    public void play(){
        System.out.println("적군을 향해 오로지 전진 앞으로!");
    }
}
```
```java
public class Children {
    void runContext(ToyStrategy toyStrategy){
        System.out.println("놀이 시작");
        toyStrategy.play();
        System.out.println("놀이 끝");
    }
}
```
```java
public class MainClient{
    public static void main(String[] args){
        Children children = new Children();

        // 아이에게 비비 인형을 주며 놀도록 한다.
        children.runContext(new ToyBibi());
        // 아이에게 탱크 장난감을 주며 놀도록 한다.
        children.runContext(new ToyTank());
        
    }
}
```
실행 결과는 다음과 같을 것이다.

```text
놀이 시작
비비의 팔다리를 이렇게~ 저렇게~
놀이 끝
놀이 시작
적군을 향해 오로지 전진 앞으로!
놀이 끝
```

위의 예시처럼 컨텍스트에 어떤 객체를 주입하냐에 따라 다양한 전략을 취하면서 컨텍스트를 실행할 수 있다. 컨텍스트(Context)라는 단어가 일상적인 대화에서 흔히 사용되는 영단어는 아니지만
학문적인 용어로써 혹은 생활 교양단어로써 다양한 분야와 서적에서 활용된다. 분야에 따라 의미의 차이는 존재하지만 일반적으로 '맥락'이라고 하는 의미에 가깝게 사용되며,
프로그래밍에서도 이와 비슷한 의미로 이해할 수 있다. **어느 맥락에서 어떤 객체를 활용하냐에 따라 다양한 결과물을 만들어 낼 수 있는 것**이다. 전략패턴을 한 문장으로 정리하면 다음과 같다.
> 클라이언트가 컨텍스트에서 실행시킬 전략을 주입하는 패턴

# Template Callback
템플릿 콜백 패턴은 전략패턴의 변형이다. 스프링의 3대 프로그래밍 모델 중 하나인 DI(Dependency Injection, 의존성 주입)에서 사용하는 특별한 형태의 전략 패턴이다.
**템플릿 콜백 패턴은 전략 패턴과 모든 것이 동일하지만 전략을 익명 내부 클래스로 정의해서 사용한다는 차이가 있다.** 앞에서 살펴본 전략패턴의 예시를 템플릿 콜백 패턴으로 변경해보자.

```java
public interface ToyStrategy{
    public abstract void play();
}
```
```java
public class Children {
    void runContext(ToyStrategy toyStrategy){
        System.out.println("놀이 시작");
        toyStrategy.play();
        System.out.println("놀이 끝");
    }
}
```
```java
public class MainClient{
    public static void main(String[] args){
        Children children = new Children();

        children.runContext(new ToyStrategy(){
            @Override
            public void play(){
                System.out.println("비비의 팔다리를 이렇게~ 저렇게~");
            }
        });
        children.runContext(new ToyStrategy(){
            @Override
            public void play(){
                System.out.println("적군을 향해 오로지 전진 앞으로!");
            }
        });
    }
}
```
위의 예시 처럼 `runContext()`를 실행할 때 익명클래스를 활용하여 인터페이스의 추상메서드를 구현함으로써 객체를 전달하고 있다. 위의 예시를 실행해도 
전략패턴의 출력과 똑같이 나올 것이다. 그러나 ToyStrategy 인터페이스를 구현하고 오버라이드 하는 과정에서 사용할 때 마다 중복된 코드가 생성된다는 것을 알 수 있다.
다음과 같이 리팩토링을 실시해보자.

```java
package com.practice.refatoring;

public class Children {
    void runContext(String toyStory){
        System.out.println("놀이 시작");
        playWithToy(toyStory).play();
        System.out.println("놀이 끝");
    }

    void runContext(Toy toy) {
        System.out.println("놀이 시작");
        playWithToy(toy).play();
        System.out.println("놀이 끝");
    }

    private ToyStrategy playWithToy(final String toyStory){
        return new ToyStrategy(){
            @Override
            public void play(){
                System.out.println(toyStory);
            }
        };
    }

    private ToyStrategy playWithToy(final Toy toy){
        return new ToyStrategy(){
            @Override
            public void play(){
                toy.setUp();
                toy.execute();
                toy.tearDown();
            }
        };
    }
}
```
```java
package com.practice.refatoring;

public class MainClient{
    public static void main(String[] args){
        Children children = new Children();
        Toy lego = new Lego();
        
        children.runContext("비비의 팔다리를 이렇게~ 저렇게~");
        children.runContext("적군을 향해 오로지 전진 앞으로!");
        children.runContext(lego);
    }
}
```

ToyStrategy 인터페이스를 구현하고 오버라이드 하는 과정의 코드를 컨텍스트 내부에서 공통적으로 처리하여 중복을 없애고, 클라이언트에서는 최소한의 변경사항들만
전달함으로써 전보다 깔끔하고 직관적으로 코드를 작성하고 있음을 알 수 있다. 리팩토링의 예시에서 Toy 타입의 객체를 인자로 받는 메서드를 오버로드하여
좀 더 다양한 동작을 수행할 수 있도록 구현하고 있음을 알 수 있다. Toy와 Lego 클래스의 코드예시는 안 나와 있지만 서로 상속관계의 구현이라고 생각하고 예시를 이해해보자.     

참고로 클라이언트가 컨텍스트의 템플릿 메소드를 호출하면서 콜백 오브젝트를 전달하는 것은 메소드 레벨에서 일어나는 의존성 주입이라고 할 수 있다.
Children의 놀이라고 하는 컨텍스트는 클라이언트에서 전달해주는 콜백 오브젝트에 의존하여 실행되고 있다는 것과 
Toy 타입의 콜백 객체가 자신을 생성한 클라이언트 정보를 직접 참조한다는 것에 주목할 필요가 있다.

전략 패턴의 전략은 여러 개의 메소드를 가진 인터페이스로 사용하는 것과 달리 템플릿 콜백 패턴의 콜백은 보통 단일 메소드 인터페이스를 사용한다.
콜백은 일반적으로 하나의 메소드를 가진 인터페이스를 구현한 익명 클래스로 만들기 때문에, 람다를 활용하여 보다 직관적으로 코드를 작성할 수 있다는 것 또한
추가적인 장점이다. 전략패턴과 템플릿 콜백 패턴은 모두 개방 폐쇄 원칙과 의존 역전 원칙이 잘 적용된 설계 패턴이며, 
템플릿 콜백패턴에 대해 한 문장으로 정리하면 다음과 같다.

> 전략을 익명 내부 클래스로 구현한 전략 패턴
