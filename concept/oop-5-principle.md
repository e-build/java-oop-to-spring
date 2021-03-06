# 객체지향 설계 5원칙
<u>***목차***</u>
- [SRP(Single Responsibility Principle)](#srpsingle-responsibility-principle)
- [OCP(Open Closed Principle)](#ocpopen-closed-principle)
- [LSP(Liskov Substitution Principle)](#lspliskov-substitution-principle)
- [ISP(Interface Segregation Principle)](#ispinterface-segregation-principle)
- [DIP(Dependency Inversion Principle)](#dipdependency-inversion-principle)


객체지향 설계 5원칙(SOLID)은 응집도는 높이고, 결합도는 낮추라는 고전 원칙을 객체 지향의 관점에서 재정립한 것이다. 
<u>***결합도***</u>란 모듈(클래스)간의 상호 의존 정도로서 결합도가 낮으면 모듈 간의 상호 의존성이 줄어들어 객체의 재사용이나 수정, 유지보수가 용이하다.
데이터 결합도, 스탬프 결합도, 컨트롤 결합도, 외부 결합도, 공유 결합도, 내용 결합도를 예로 들 수 있다.
<u>***응집도***</u>란 하나의 모듈 내부에 존재하는 구성 요소들간의 기능적 관련성으로, 
응집도가 높은 모듈은 하나의 책임에 집중하고 독립성이 높아져 재사용이나 기능의 수정, 유지보수가 용이하다.
기능 응집도, 통신 응집도, 절차 응집도, 시간 응집도, 논리 응집도, 우연 응집도가 그 예이다.

SOLID는 객체 지향 프로그램을 구성하는, 속성, 메서드, 클래스, 객체, 패키지, 모듈, 라이브러리, 프레임워크, 아키텍처 등 
다양한 곳에서 다양하게 적용된다. SOLID는 객체 지향 4대 특성을 발판으로 하고 디자인 패턴의 뼈대이며 스프링 프레임워크의 근간이기도 하다. 
# SRP(Single Responsibility Principle)
***단일 책임 원칙***이라고도 하며 로버트.C.마틴의 다음 격언이 SRP 원칙의 유명한 격언이다.
> 어떤 클래스를 변경해야 하는 이유는 오직 하나뿐이어야 한다.

예를 들어 연필 클래스와 연필 클래스에 의존하는 다양한 클래스가 있다고 해보자. 그래서 연필에는 다음과 같은 메서드들이 있다.

```java
class 연필{
    String type; // weapon, study, drawing
    int length;
    int thickness;
    int offensePower;
  
    public void 찌르기(){ ... }
    public void 휘두르기(){ ... }
    public void 낙서하기(){ ... }
    public void 글쓰기(){ ... }
    public void 스케치하기(){ ... }
    public void 드로잉하기(){ ... }
}
```
위에 예시로든 연필클래스에는 다음과 같은 역할과 그 역할을 수행하기 위한 각각의 메서드들이 있다.

| 역할 | 메서드 |
|---|---|
| **흉기** | 찌르기(),  휘두르기() |
| **공부** | 낙서하기(),  글쓰기() |
| **그림** | 스케치하기(),  드로잉하기() |

연필이라고 하는 하나의 사물을 나타내는 클래스일뿐이지만 어떤 역할로 수행되느냐에 따라서 아예 다른 용도로 사용 되는 것을 알 수 있다. 이와 같이 클래스가 하나의 역할만을 수행하는 것이 아니고 
여러 책임을 가지고 있을 때 객체지향에서는 '악취가 풍기는 코드' 라는 표현을 사용한다. 오직 하나의 이유로만 클래스를 수정하기 위해선 클래스가 하나의 역할과 책임만 가질 수 있도록
***역할과 책임***이라는 기준에 따라 클래스를 쪼개야 하는 것이다.

위의 연필클래스를 흉기용연필, 공부용연필, 그림용연필 클래스로 분리하고 흉기용연필 클래스에서만 offensePower 필드를 갖게 하면 단일 책임 원칙이 적용되는 것이다.
클래스를 분리했을 때 분리된 클래스간에 공통된 특성이 있다면 공통된 특성을 가진 연필클래스를 상위클래스로 둘 수 있지만, 공통된 특성이 없다면 연필 클래스는 제거할 수도 있다.

하나의 속성이 여러 의미를 갖는 경우도 단일 책임 원칙을 지키지 못하는 경우이다. 예를 들어 thickness 필드가 연필의 굵기를 나타낼 때, 
심의 굵기를 나타낼 때의 의미를 둘 다 가진다면 소스 코드 상에서 if 문을 덕지덕지 사용해야 하는 상황이 발생하게 될 것이다.

그 다음으로 낙서하기() 메서드를 통해 메서드 수준의 SRP를 생각해보자.
```java
public void 낙서하기(){
    if (this.type.equals("drawing")){
        // --- 눈 앞의 사물을 그린다.
    } else if (this.type.equals("study")) {
        // --- 각종 도형을 그린다.
    }
}
```
낙서하기() 메서드는 공부용도로 사용할 때와 그림용도로 사용할 때의 동작이 다른데 하나의 메서드에서 두 가지 행위를 모두 구현하려 하기 때문에
SRP를 위반하고 있다고 볼 수 있다. 이를 역할별로 클래스를 쪼갠다면 쪼개진 각각의 클래스 내부의 메서드에서 하나의 책임만 수행하도록 구현할 수 있을 것이다.

적절한 예시를 생각하다가 어쩌다 연필이라 클래스로 다소 억지스러운 예시를 계속 사용하게 되었는데, 충분히 전달되지 않았다면 독자들이 직접 예시를 고민해보며 SRP에 대한 
개념을 좀 더 다질 수 있길 바란다.

> 단일 책임 원칙은 객체 지향 4대 특성 중 [추상화](https://github.com/e-build/java-oop-to-spring/blob/main/concept/oop-on-java.md#추상화-Abstraction) 와 가장 관계가 깊다. 리팩토링을 진행할 때, 애플리케이션의 경계를 정하고 추상화를 통해
> 클래스들의 속성과 메서드를 설계할 때 단일 책임 원칙에 위배되지 않도록 클래스를 설계하는 사고습관이 요구된다.

# OCP(Open Closed Principle)
개방 폐쇄 원칙이라고도 하며, 자신의 확장에는 열려있지만 환경의 변화에는 폐쇄적으로 설계하는 것을 의미한다. 즉, 기존의 코드를 변경하지 않으면서 기능을 확장할 수 있도록 설계가 되어야 한다는 의미이다.
"개방"과 "폐쇄"라고 하는 단어의 역설때문에 처음 이해하는 입장에서 상당히 난해할 수 있다. 다음 예를 살펴보자.
```java
public class ElevatorA {

    public void move(){

    }

    public void openLeftRight(){

    }
}

```
```java
public class ElevatorB {

    public void move(){
        [...]
    }
    
    public void openUpDown(){
        [...]
    }
}
```
위와 같이 두 개의 엘레베이터 회사가 있고, 각기 다른 방식으로 문이 개방된다는 것을 알 수 있다. 그렇다면 엘레베이터 클래스들을 사용하는
건물 클래스에서는 어떤 엘레베이터를 사용할 것인지에 따라 `Building` 클래스의 멤버필드로 선언해놓고 사용하는 곳에서 `openLeftRight()`혹은 `openUpDown()`을
통해 엘레베이터 문을 열게 될 것이다. 
```java
public class Building {

    ElevatorA elevatorA = new ElevatorA();
    
    void movePeople() {
        [...]
        
        elevatorA.openLeftRight();
        
        [...]
        
        elevatorA.move();
        
        [...]
    }
}

```
`Building` 클래스를 보면 ElevatorA 멤버필드를 초기화하고 `movePeople()` 메서드 내에서 ElevatorA 인스턴스가 사용되고 있는 것을 볼 수 있다. 
이와같이 A 엘레베이터를 이용해서 지금 당장 건물을 설계하는 것은 아무런 문제가 없어보일지도 모른다. 
그러나 건물이 설계된 이후 엘레베이터가 고장났는데 A 회사가 망해서 더이상 시스템 설비를 받을 수 없는 상황으로 어쩔 수 없이
모든 엘레베이터로 B 회사의 것으로 변경해야 한다면 어떨까? 

다음의 코드와 같이 `Building` 클래스의 멤버필드를 ElevatorB로 수정하고 인스턴스를 할당하고,
`movePeople()`메서드 내에서 `openLeftRight()` 메서드를 호출하도록 변경하게 해야 할 것이다.
```java
public class Building {

    ElevatorB elevatorB = new ElevatorB();

    void movePeople() {
        [...]
        
        elevatorB.openLeftRight();
        
        [...]
        
        elevatorB.move();
        
        [...]
    }
}
```
엘레베이터 회사를 변경하는 상황에서 엘레베이터에 의존하고 있는 `Building` 클래스의 코드를 수정했다. 환경의 변화에 폐쇄적으로 설계되었다고 할 수 있을까?

엘레베이터를 예시로 들었기 때문에 현실에서 쉽게 공감하지 못할 수도 있지만, 소프트웨어 세상에서 기능이 변경되거나 확장되는 상황은 정말 빈번하게 발생한다. 
어쩌면 현실세계의 비즈니스는 이보다 훨씬 더 복잡한 변화와 통제할 수 없는 변인들이 존재할 수도 있다. 

그렇다면 엘레베이터 회사가 어딘지와 상관없이 Building 클래스가 설계될 수 있도록 수정해보자.
<table>
    <thead>
        <tr>
            <th>클래스</th>
            <th>메서드</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan=2> A 엘레베이터 회사</td>
            <td>문을 좌우로 개방하다()</td>
        </tr>
        <tr>
            <td>이동하다()</td>
        </tr>
        <tr>
            <td rowspan=2>B 엘레베이터 회사</td>
            <td>문을 위아래로 개방하다()</td>
        </tr>
        <tr>
            <td>이동하다()</td>
        </tr>
    </tbody>
</table>

<table>
    <thead>
        <tr>
            <th>인터페이스</th>
            <th>메서드</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="2"> 엘레베이터 </td>
            <td > 문을 개방하다() </td>
        </tr>
        <tr>
            <td > 이동하다() </td>
        </tr>
    </tbody>
</table>
엘레베이터라는 인터페이스를 만들어서 A, B 엘레베이터 회사들이 해당 인터페이스를 구현하게 한다. 그렇다면 엘레베이터를 사용하는 건물 입장에선
어떤 회사의 엘레베이터를 사용하는 지 알 필요없이 단순히 `문을 개방하다()`,`이동하다()` 메서드를 통해 엘레베이터를 동작시킬 수 있게 된다.
이는 다음과 같이 Elevator를 인터페이스로 추상화할 수 있다.

```java
public interface Elevator {

    public void move();
    public void open();
}
``` 
```java
public class ElevatorA implements Elevator{

    @Override
    public void move() {
        [...]
    }

    @Override
    public void open() {
        [...문이 좌우로 열리도록 동작...]
    }
}
```
```java
public class ElevatorB implements Elevator{

    @Override
    public void move() {
        [...]
    }

    @Override
    public void open() {
        [...문이 위아래로 열리도록 동작...]
    }
}
```
기존의 Building 클래스는 멤버필드로 구체클래스(Concreate Class)타입의 인스턴스를 선언해놨기 때문에 다른 타입이 필요한 상황이면
당연히 기존 코드를 수정할 수 밖에 없었다. 또한 멤버필드 선언과 동시에 `new` 연산자로 인스턴스를 생성하여 할당했기 때문에, 이미 컴파일 시점에 
어떤 객체를 사용하게 될지 결정되었다. 이는 의존하고 있는 객체의 변화에 대해 유연하게 대처하기 힘든 중요한 원인이 된다.
따라서 멤버필드의 타입을 인터페이스로 정의하고 인스턴스는 외부에서 주입받을 수 있도록 생성자의 인자를 통해 인스턴스를 할당받도록 수정할 것이다.

```java
public class Building {

    private Elevator elevator;

    public Building(Elevator elevator){
        this.elevator = elevator;
    }
    
    void setElevator(Elevator elevator){
        this.elevator = elevator;
    }

    void movePeople() {
        [...]
        
        elevator.open();
        
        [...]

        elevatorB.move();
        
        [...]
    }
}
```
위의 과정을 정리해보면 다음과 같다.
1. 엘레베이터를 사용하고 있는 곳(Building)에서 구체클래스(ElevatorA, ElevatorB)로 멤버필드 선언과 동시에 인스턴스를 할당하여 사용하고 있었다.
2. 엘레베이터 회사를 바꿔야 하는데, 엘레베이터 구체클래스를 사용하고 있는 기존 코드(Building)를 전부 수정해야 하는 문제가 발생했다.

***리팩토링***
1. 엘레베이터의 책임을 추상화하여 인터페이스로 분리한다.
2. ElevatorA, ElevatorB 클래스가 인터페이스를 구현하게 한다.
3. Building 클래스의 멤버필드 타입으로 Elevator 인터페이스로 선언하고 인스턴스를 생성자를 통해 외부에서 주입받는다.
4. 엘레베이터 회사를 바꿔야 한다면, 기존 코드를 수정하지 않고 수정자(setElevator())를 통해 변경할 수 있다.
```java
public class Mainclass(){
    
    public static void main(String[] args){
        
        Building building = new Building(new ElevatorA());
        
        [...]
        
        building.setElevator(new ElevatorB());
    }
}
```
즉, OCP 원칙이 잘 지켜지려면 추상화가 우선 잘 되어야 하고, 상황에 유연하게 대처할 수 있도록 의존하고 있는 타입의 인스턴스를 외부에서 주입받아야 한다.
자바에서도 이러한 OCP 원칙을 토대로 설계된 유용한 API들을 제공하고 있는데, 그 중 좋은 예가 JDBC(Java Database Connectivity) API이다.
JDBC를 구현하여 각각의 DB벤더사(MySQL, MSSQL, Oracle, ...)들이 데이터베이스 커넥션을 제공하지만 JDBC 사용자의 입장에서는 Connection을 설정하는 것 외에는 
DB 벤더사에 따라 API를 다른 방식으로 사용하지 않을 수 있다. 

개방 폐쇄 원칙을 따르지 않는다고 해서 객체 지향 프로그램을 구현하는 것이 불가능한 것은 아니지만, 객체지향 프로그래밍의 가장 큰 장점인 유연성, 재사용성, 유지보수성 등을 얻을 수 없다.
스프링 프레임워크와 같은 좋은 프레임워크일수록 개방-폐쇄 원칙을 교과서적으로 활용하고 있음을 확인할 수 있다.

# LSP(Liskov Substitution Principle)
> 서브 타입은 언제나 자신의 기반 타입(Base Type)으로 교체할 수 있어야 한다. - 로버트 C.마틴
 
상속에 대해 설명하면서 객체 지향의 상속은 다음의 조건을 만족해야 한다고 전달한 바 있다.
* 하위 클래스 is a kind of 상위 클래스
* 구현 클래스 is able to 인터페이스

위 두개의 문장대로 구현된 프로그램이라면 이미 리스코프 치환원칙을 잘 지키고 있다고 할 수 있다. 
# ISP(Interface Segregation Principle)
> 클라이언트는 자신이 사용하지 않는 메서드에 의존관계를 맺어서는 안된다. - 로버트 C.마틴

인터페이스 분리 원칙이라고도하며, 경우에 따라 특정 역할만 수행할 수 있도록 강제하는 기능 제공하는 설계라고 할 수 있다. 
특정 인터페이스를 구현하는 클래스는 강제적으로 인터페이스의 메서드들을 구현해야만 한다. 따라서 충분히 확장성 있는 인터페이스를 제공하기 위해선 반드시 필요한 메서드들만 구현하도록
강제해야 하는데, '반드시 필요한' 이라는 기준은 각기 책임에 따른 적절한 추상화와 관련이 깊을 것이다. 따라서 인터페이스를 통해 메서드를 외부에 제공할 때는 
최소한의 메서드만 제공하라는 것이 ISP 원칙에 따른 설계에서 정말 중요한 점이다. 최소한이라는 단어가 단순히 애매모호한 표현을 사용해서 인터페이스의 역할을 추상화하라는 의미가 
아니라 인터페이스는 그 역할에 충실한 최소한의 기능만 공개하라는 것이다.   

어떻게 보면, SRP와 ISP는 같은 문제에 대한 두 가지의 다른 해결책이라고 할 수 있는데, 프로젝트 요구사항과 설계자의 취향에 따라 단일 책임 원칙이나 
인터페이스 분할 원칙 중 하나를 선택해서 설계할 수 있다. 하지만 특별한 경우가 아니라면 단일 책임 원칙을 적용하는 것이 더 좋은 해결책인 경우가 많다.  
# DIP(Dependency Inversion Principle)
> 추상화된 것은 구체화된 것에 의존하면 안된다. 구체화된 것이 추상화된 것에 의존해야 한다. 자주 변경되는 구체(Concreate)클래스에 의존하지 마라. - 로버트 C.마틴 

의존 역전 원칙이라고도 한다. 의존 관계를 맺을 때 변화하기 쉬운 것 또는 자주 변화하는 것보다는 변화하기 어려운 것, 거의 변화가 없는 것에 의존하라는 것이다. 
한마디로 구체적인 클래스보다 인터페이스나 추상 클래스와 관계를 맺으라는 것이다.
