<u>***목차***</u>
- [리팩토링](#리팩토링)
  + [@Inject 개선](#@Inject-개선)

# 리팩토링
### @Inject 개선
1. 인스턴스 생성과 주입 작업은 `BeanFactory`가 담당
2. 현재 빈 클래스의 상태 정보를 별도의 클래스로 추상화하여 관리 => `BeanDefinition`

`BeanDefinition`
`BeanDefinitionRegistry`
`ClasspathBeanDefinitionScanner`
