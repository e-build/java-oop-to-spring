<i><u>***목차***</i></u>
- [한계](#한계)
- [리팩토링](#리팩토링)
    + [설계](#설계)
- [리팩토링과 테스트](#리팩토링과-테스트)
 
# 한계

# 리팩토링
### 설계
1. 변하는 코드 분리
    1. SQL 생성, 파라미터 세팅 로직 메서드로 분리
    2. 분리한 메서드를 새로 클래스를 추가하여 이동 => 쿼리 생성, 파라미터 세팅  
       - 새로 추가된 클래스에서 분리된 메서드들을 추상메서드로 제공하고 반복적으로 발새하는 중복 로직을 상위클래스가 구현 [(템플릿메서드 패턴)](https://github.com/e-build/java-oop-to-spring/blob/main/concept/design-pattern.md#template-method)
    3. 의존관계 제거 
    4. 클래스 내의 추상메서드를 각각의 인터페이스로 분리
       - 추상 메서드 간의 의존관계가 발생하여 호출하는 쪽에서 굳이 필요하지 않은 추상메서드까지 구현해야 하는 경우가 발생할 수 있다.
       이 때, 각각의 추상메서드를 인터페이스로 분리하고 메서드의 인자로 전달하여 사용한다면 좀 더 유연하게 호출하는 것이 가능해진다. [(템플릿 콜백 패턴)](https://github.com/e-build/java-oop-to-spring/blob/main/concept/design-pattern.md#template-callback)
2. SQLException을 런타임 Exception으로 처리
   * SQLException이 컴파일타임 Exception이기 때문에 RuntimeException으로 바꾸는 작업을 진행
   * RuntimeException을 확장하는 DataAccessException 클래스 추가
   * SQLException을 캐치하는 부분에서 DataAccessException을 throw 함으로써 컴파일타임 Exception 제거
3. 제네릭
4. 가변인자

# 리팩토링과 테스트
- 리팩토링은 새로운 기능을 추가하지 않으면서 설계를 개선하는 작업이다. 따라서 리팩토링은 기존의 기능에 문제가 발생하지 않도록 
  최대한 안전하게 진행해야 한다.
- 기존의 기능이 정상적으로 동작하는 지 확인할 수 있도록 리팩토링 과정에서 테스트를 통해 기능이 정상적으로 동작하는 지 반드시 확인해야 한다.
  리팩토링과 테스트의 주기가 짧아야하며, 리팩토링 과정에서 일시적으로 중복 코드가 생기거나 원치 않는 이름을 사용해야 하는 경우도 있다. 