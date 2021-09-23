<i><u>***목차***</i></u>
- [한계](#한계)
- [리팩토링](#리팩토링)
    + [설계](#설계)
- [리팩토링과 테스트](#리팩토링과-테스트)
 
# 한계
1. UserDao, RecipeDao 클래스의 중복된 코드
   - Connection을 가져와서 매번 try-catch 절을 사용하여 SQLException을 처리해야 한다. 
     데이터베이스에 접근할 때 비즈니스 로직에 따라 변화하는 로직보다 불필요하게 중복해서 작성해야 하는 코드들이 너무 많다.   
2. 소스코드의 가독성 저하 
   - SQLException 컴파일 Exception 때문에 매번 try-catch 절 처리를 해야 함. 너무 무분별하게 사용됨으로써 소스코드의 가독성을 떨어뜨리는 주된 이유.

> <u>***Complietime Exception / Rumtime Exception 사용 가이드***</u>
> 
> ***Complietime Exception***
> - API를 사용하는 모든 곳에서 이 예외를 처리해야 하는가?
> - 예외가 반드시 메서드에 대한 반환 값이 반환되어야 하는가?
> 
> ***Runtime Exception***
> - API를 사용하는 소수 중 이 예외를 처리해야 하는가?
> - API를 사용하는 코드에서 Exception을 catch하더라도 에러에 대한 정보를 통보 받는 것 외에 아무것도 할 수 있는 것이 없을
> - API를 사용하는 모든 코드가 Exception을 catch하도록 강제하지 않는 것 이 좋다.
> - 대부분 Exception에 대해 문서화하고 API를 사용하는 곳에서 Exception에 대한 처리를 결정하도록 하는 것이 좋다.
> 
> 
> `Expert one-on-one J2EE 설계와 개발`

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
   > <u>***변하는 코드와 변하지 않는 분리***</u> 
   > 
   > 메서드가 한 가지 작업만 처리하도록 작은 단위로 분리하다보면 중복코드가 명확하게 
   > 드러나는 경험을 종종 할 수 있다. 따라서 비즈니스 로직의 복잡성으로 인해 
   > 변하는 부분과 변하지 않는 부분을 명확하게 정의하기 어려워 보인다면, 
   > 메서드가 한 가지 작업만 수행할 때까지 계속해서 분리해보자.
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