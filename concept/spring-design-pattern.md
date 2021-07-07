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
어댑터를 사용하는 쪽에서는 어댑터를 이용해 service()라는 동일한 메서드명으로 두 객체의 메서드를 호출하는 것을 볼 수 있다.
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
싱글턴 패턴이란 인스턴스를 하나만 만들어 사요하기 위한 패턴이다. 커넥션 풀, 스레드 풀, 디바이스 설정 객체 등과 같은 경우 
인스턴스를 여러개 만들게 되면 불필요한 자원을 사용하게 되고, 또 프로그램이 예상치 못한 결과를 낳을 수 있다. 싱글턴 패턴은 오직 인스턴스르 하나만 만들고 그것을 계속해서 재사용한다.
싱글턴 패턴을 적용하기 위해선 다음 세가지가 요구된다.

* new 연산자를 사용할 수 없도록 생성자에 private 접근제어자를 지정한다.
* 유일한 단일 객체를 반환할 수 있는 정적 메서드가 필요하다.
* 유일한 단일 객체를 참조할 정적 참조 변수가 필요하다.

# Template Method
말그대로 메서드의 템플릿을 만들고 상속받는 하위클래스에서 템플릿 중 일부 메서드를 오버라이드하여 사용할 수 있도록 하는 설계 패턴이다. 다음의 예를 보자
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
남자와 여자 클래스 모두 play() 메서드를 호출하면 컴퓨터를 키고 끄는 동작은 동일한데 중간에 게임을 하느냐 유튜브를 보느냐의 차이가 존재한다. 
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
위와 같이 Human 추상클래스에서 play()라고 하는 메서드의 템플릿을 제공함으로써 남자와 여자 클래스가 play()를 실행 할 때 요구되는 중복을 제거하고, 
각기 다르게 동작하는 부분은 doContents() 라고 하는 추상메서드를 오버라이딩함으로써 구현하도록 하였다. 남자클래스는 추가적으로 컴퓨터를 끄는 메서드를 오버라이딩하여 
끄지않고 절전모드로 변경하게 하였는데, 이처럼 상위클래스에서 이미 구현까지 되어있으며 하위메서드에서 선택적으로 구현하도록 하는 메서드를 Hook이라고 한다.

즉, ***템플릿 메서드는 상위 클래스의 견본 메서드에서 하위 클래스가 오버라이딩한 메서드를 호출하는 패턴***이라고 할 수 있다.

# Factory Method
팩토리는 흔히 알다시피 공장을 의미한다. 공장은 일반적으로 물건을 생산하는데, 객체지향에서 팩터리는 객체를 생성한다.
# Strategy

# Template Callback
