***목차***
# DI
### DI가 필요한 이유
문제인식이 없는 상태에서 새로운 지식을 학습한다는 것은 그만큼 어렵고 동기부여가 되지 않는다. 현재 상황을 문제라고 여기지 않기 때문에
변화에 대한 필요성을 느끼는 것이 어렵고, 개념이 가지는 실용적인 가치를 구체적으로 상상하기 힘든 것이다. 
따라서 본격적으로 DI가 무엇인지 알아보기 전에, DI라고 하는 개념이 왜 필요하며 구체적으로 어느 상황에서 효율적으로 활용될 수 있는 지에 대한 시간을 먼저 가져보도록 하자.

DI는 ***의존관계***(dependency)와 관계가 깊다. 의존관계란 객체 혼자 모든 일을 처리하기 힘들기 때문에, 다른 객체에게 작업을 위임하면서 발생한다.
우리는 객체가 하나의 책임만을 수행할 수 있도록 클래스를 구분하여 설계해야 하기 때문에 대부분의 애플리케이션에서는 의존관계가 발생할 수 밖에 없다. 
그러나 모듈(객체, 클래스, 메서드, 패키지, 애플리케이션 등) 간의 결합도를 낮추고 응집도를 높이는 설계를 지향하는 상황에 무작위로 발생하는 의존관계는 
애플리케이션 모듈간의 커플링을 높이게 되고, 비즈니스 변화속에서 충분히 유연하게 개발하는 것을 어렵게 만든다. 

DI는 객체 간의 의존관계를 어떻게 해결하느냐에 따른 새로운 접근 방식이다. 지금까지 우리는 의존관계에 있는 객체를 사용하기 위해 
객체를 직접 생성하고 사용하는 방식으로 구현했다. 
```java
public class MainClass{
    
    private final Toy toy;
    
    public MainClass(){
        toy = new Toy(); // 객체를 직접 생성하여 toy 참조변수에 할당
    }
    
    public static void main(String[] args){
        toy.play(); // 객체 사용
    }
}
```
위의 코드를 보면 `MainClass`안에서 Toy 객체를 생성하여 사용하고 있다.
이 같은 방식으로 구현할 경우, 유연한 개발을 하는 데 한계가 있기 때문에 ***인스턴스를 생성하는 책임과 사용하는 책임을 분리***하자는 것이 DI의 핵심이다.
```java
public class MainClass {

    private final Toy toy;
    
    // 생성자를 통해 의존관계의 객체를 주입받음.
    public MainClass(Toy toy) {
        toy = toy;
    }
}
```

그렇다면, DI가 어떻게 애플리케이션 개발에 유연성을 부여하는 지 살펴보기 위해 다음 예시를 보자.
```java
public class HumanMessageProvider{
    
    public String getGenderMessage(){
        Human human = Human.getDefaultInstance();
        return human.getGender();
    }
}
```
```java
public class HumanMessageProviderTest{
    
    @Test
    public void 남성() throws Exception{
        HumanMessageProvider provider = new HumanMessageProvider();
        assertEquals("남성", provider.getGenderMessage());
    }

    @Test
    public void 여성() throws Exception{
        HumanMessageProvider provider = new HumanMessageProvider();
        assertEquals("여성", provider.getGenderMessage());
    }
}
```
테스트 코드를 실행하면 `Human.getDefaultInstance()` 에서 리턴하는 성별의 테스트 케이스만 성공하고, 테스트 케이스 하나는 반드시 실패할 것이다.
둘 다 성공할 수 없는 이유는 `HumanMessageProvider`와 `Human` 이 `getGenderMessage()` 메서드 내부에서 강하게 결합된 의존관계를 맺고 있기 때문이다.
소스코드를 컴파일할 때 이미 `Human` 인스턴스가 결정되기 때문에, 테스트하는 시점에 `Human` 인스턴스를 수정할 수 없게 된다. 따라서 Human 인스턴스 생성을
`getGenderMessage()` 내부에서 하는 것이 아니라 외부에서 Human 인스턴스를 생성한 후 전달하는 구조로 바꿔야 한다.
```java
public class HumanMessageProvider{
    
    public String getGenderMessage(Human human){
        return human.getGender();
    }
}
```
```java
public class HumanMessageProviderTest{
    
    @Test
    public void 남성() throws Exception{
        HumanMessageProvider provider = new HumanMessageProvider();
        assertEquals("남성", provider.getGenderMessage(createHuman("남성")));
    }

    @Test
    public void 여성() throws Exception{
        HumanMessageProvider provider = new HumanMessageProvider();
        assertEquals("여성", provider.getGenderMessage(createHuman("남성")));
    }
    
    private Human createHuman(String gender){
        Human human = Human.getDefaultInstance();
        human.setGender(gender);
        return human;
    }
}
```
이처럼 객체 간의 의존관계에 대한 결정권을 의존관계를 가지는 객체(HumanMessageProvider)가 가지는 것이 아니라,
외부의 누군가가 담당하도록 맡겨버림으로써 좀 더 유연한 구조로 개발하는 것을 ***DI*** 라고 한다.
일반적으로 유연한 구조의 애플리케이션은 변화를 최소화하면서 확장하기도 쉽고, 테스트하기도 쉽다는 것을 의미한다.
# AOP
# PSA


