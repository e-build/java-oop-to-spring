
### 참고 서적
```
- 이펙티브 자바 (조슈아 블로크 지음)
- 스프링 입문을 위한 자바 객체지향의 원리와 이해 (김종민 지음)
- 자바 웹 프로그래밍 NEXT STEP (박재성 지음)
```

# 저장소 설명  
본 저장소는 자바의 객체지향 철학이 스프링 프레임워크 내에서 어떤 디자인패턴과 용법으로 활용되었는 지 탐구하기 위해 만들어졌습니다.
현업에서 스쳐지나가는 자바와 OOP에 대한 수많은 고민과 경험들을 보다 구조적이고 도식화된 형태로 정리하고자하며, 해당 목적을 달성하는 과정에서 꾸준한 학습과 탐구를 지속하고자 합니다.

OKKY, 페이스북 그룹 등과 같은 여러 개발 커뮤니티의 수많은 선배 개발자들이 강조하는 자바 개발자로써의 역량들에 대한 비판적 시각과,
현업의 문제 해결과정에서 요구되는 역량에 대한 답을 찾아가는 과정이 될 것이라 생각합니다.

위에 명시된 서적들을 정리하고 실습 코드 예제를 고민하며 개념을 내재화하는 학습과정이, 신기술에 대한 학습이나 가슴뛰는 서비스를 만드는 과정보다 
다소 지난한 과정일 수 있지만 정확히 내재화된 이 개념들은 추후 자바 개발자로써의 생활에 큰 밑거름이 될 것이라 생각합니다. 

얼마나 정확하고 깊게 탐구할 수 있을지 혹은 얼마나 유용한 저장소로 활용될 수 있을지는 모르겠지만, 단계별 실습과 설명을 통하여 저와 같은 주니어 개발자들의 자바 학습에 조금이라도 실용적인 저장소가 되었으면 좋겠습니다.

# 저장소 구성
스프링에 적용되어있는 객체지향 설계를 실습하기 위해 박재성님의 '자바 웹 프로그래밍 NEXT STEP' 서적을 참고하였고, 구성은 다음과 같습니다.
개념을 설명하는 `concept` 디렉토리와 자바 소스코드로 구성되어 있습니다. 하나의 저장소에서 <i>**개념에 대한 학습**</i>과 <i>**실제 적용이 어떻게 이루어지는 지**</i> 보다 간편하게 파악할 수 있도록 
구성하고자 하였습니다.  모든 브런치는 가장 최신으로 정리된 `concept` 디렉토리가 유지되고, 소스코드를 기준으로 다음과 같이 브런치를 구분하였습니다.

- [01-web-server](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/01-web-server.md) : 서블릿을 사용하지 않고, 순수 자바로 웹 어플리케이션을 구현
- [02-web-server-refactoring](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/02-web-server-refactoring.md) : no-framework-not-refactoring 브런치의 웹 요청 처리 로직 리팩토링
- [03-data-access-refactoring](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/03-data-access-refactoring.md) : Data Access 로직 리팩토링 
- [04-new-mvc](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/04-new-mvc.md) : 자바 리플렉션 API를 이용하여 애노테이션 기반의 새로운 MVC 프레임워크로 점진적 개선
- [05-new-mvc-refactoring-using-adapter](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/05-new-mvc-refactoring-using-adapter.md) : no-framework-new-mvc 브랜치에 확장성을 부여하는 리팩토링
- [07-di-container-constructor](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/07-di-container-constructor.md) : DI 프레임워크를 구현. 생성자 주입으로만 선언가능
- [08-di-container-setter-field](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/08-di-container-setter-field.md) : DI 프레임워크 수정자주입, 필드 주입 추가 
- [09-di-container-setter-field-refactoring](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/09-di-container-setter-field-refactoring.md) : DI 프레임워크 리팩토링
- [10-di-container-get-flexible](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/10-di-container-get-flexible.md) : 프레임워크를 재사용 가능하도록 유연성 확보 
- [11-di-container-with-interface](https://github.com/e-build/java-oop-to-spring/blob/main/concept/branch_describe/11-di-container-with-interface.md) : 확장가능한 부분에 대하여 인터페이스로 추상화하고, 객체간 의존관계 설정에 인터페이스를 활용하도록 수정   
 

# 목차
[1. 자바 프로그램의 구동과 메모리 구조](https://github.com/e-build/java-oop-to-spring/blob/main/concept/java-program-running-and-memory-change.md)

[2. 객체 지향 4대 특성](https://github.com/e-build/java-oop-to-spring/blob/main/concept/oop-on-java.md)

[3. 객체지향 설계 5원칙](https://github.com/e-build/java-oop-to-spring/blob/main/concept/oop-5-principle.md)

[4. 디자인패턴](https://github.com/e-build/java-oop-to-spring/blob/main/concept/design-pattern.md)





