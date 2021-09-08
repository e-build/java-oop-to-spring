<u>***목차***</u>
- [한계](#한계)
- [리팩토링](#리팩토링)

# 한계
우리는 유연성과 확장성이 필요한 경우 인터페이스를 추가하고 이 인터페이스를 구현하는 구현체를 추가하여, 보다 추상화된 수준에서 객체들 간 의존관계를 맺을 수 있도록 변경했다.
예를 들어 MVC 프레임워크를 구현할 땐 HandlerAdapter, HandlerMapping 인터페이스를 통해, 일일이 HTTP 요청과 컨트롤러를 매핑해주던 환경과 더불어 @Controller 어노테이션을 통해
선언한 컨트롤러도 함께 사용할 수 있도록 확장하였다. 이와 같이 버전을 업그레이드하면서 하위 버전을 충분히 지원해주기 위해선 잘 설계된 인터페이스가 정말 중요하다. 
스프링 프레임워크가 바로 그 대표적인 예시이며, 그러한 의미에서 우리가 만든 DI 프레임워크도 충분한 확장성을 갖출 수 있도록 인터페이스화하는 작업을 진행해볼 필요가 있을 것이다.

그 과정에서 인터페이스 기반 개발의 장점을 직접 느껴보고 객체지향 설계에 대해 다시 한번 고민해볼 수 있는 시간을 가져보자.

# 리팩토링
1. 각 기능마다 변화가 발생하는 시점이 다른 부분은 서로 다른 인터페이스로 분리함으로써 각 인터페이스마다 서로 다르게 변화, 발전할 수 있고 유연성을 확보할 수 있다.
    ```java
    package com.framework.core.di.beans.factory.support;
    
    import com.framework.core.di.BeanDefinition;
        
    public interface BeanDefinitionRegistry {
        void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition);
    }
    ```
    ```java
    package com.framework.core.di.beans.factory.support;

    public interface BeanDefinitionReader {
        void loadBeanDefinitions(Class<?>... annotatedClasses);
    }
    ```
    ```java
    package com.framework.core.di.beans.factory;

    import java.util.Set;
    
    public interface BeanFactory {
        Set<Class<?>> getBeanClasses();
        <T> T getBean(Class<T> clazz);
        void clear();
    }
    ```
    ```java
    package com.framework.core.di.beans.factory;

    import java.util.Set;
    
    public interface BeanFactory {
        Set<Class<?>> getBeanClasses();
        <T> T getBean(Class<T> clazz);
        void clear();
    }
    ```
   ```java
    package com.framework.core.di.beans.factory.config;

    import com.framework.core.di.InjectType;
    
    import java.lang.reflect.Constructor;
    import java.lang.reflect.Field;
    import java.util.Set;
    
    public interface BeanDefinition {
        Constructor<?> getInjectConstructor();
        Set<Field> getInjectFields();
        Class<?> getBeanClasses();
        InjectType getResolvedInjectMode();
    }
    ```
2. 인ㅅ