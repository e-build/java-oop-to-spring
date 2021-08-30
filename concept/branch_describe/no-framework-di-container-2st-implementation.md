<u>***목차***</u>
- [기능 추가](#기능-추가)
  + [설정 추가를 통한 유연성 확보](#설정-추가를-통한-유연성-확보)
  + [외부 라이브러리 클래스를 빈으로 등록하기](#외부-라이브러리-클래스를-빈으로-등록하기)
  + [초기화 기능 추가 ](#초기화-기능-추가)
- [리팩토링](#리팩토링)
  + [@Inject 개선](#@Inject-개선)


# 기능 추가
### 필드와 setter 메서드에 @inject 기능 추가
1. 추상화
  * [`BeanFactory`]()는 빈을 추가하고 조회하는 역할만 수행하도록 변경
  * 의존관계를 주입하는 인터페이스 [`Injector`]()를 추가한다.
2. 각 경우에 대한 구현체 구현
  * 생성자를 활용해 DI를 하고 인스턴스를 생성하는 연할은 [`ConstructorInjector`]()가 수행
  * 필드 주입을 수행하는 역할은 [`FieldInjector`]()가 수행
  * 수정자 주입을 수행하는 역할은 [`SetterInjector`]()가 수행
3. 중복 제거
  * 3개의 `Injector` 구현체를 구현하면 많은 중복이 발생한다. Injector 인터페이스와 3개의 구현체 사이에 [`AbstractInjector`]()와 같은 추상 클래스를 생성해 중복을 제거한다.
    [(템플릿 메서드 패턴)](https://github.com/e-build/java-oop-to-spring/blob/main/concept/design-pattern.md#template-method)

### 설정 추가를 통한 유연성 확보
### 외부 라이브러리 클래스를 빈으로 등록하기
### 초기화 기능 추가\

# 리팩토링
### @Inject 개선