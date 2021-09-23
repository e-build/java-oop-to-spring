<u>***목차***</u>
- [한계](#한계)
- [리팩토링](#리팩토링)

# 한계
우리는 유연성과 확장성이 필요한 경우 인터페이스와 해당 인터페이스의 구현체를 추가하여, 보다 추상화된 수준에서 객체들 간의 의존관계를 맺을 수 있도록 변경했다.
예를 들어 MVC 프레임워크를 구현할 땐 HandlerAdapter, HandlerMapping 인터페이스를 통해, 일일이 HTTP 요청과 컨트롤러를 매핑해주던 환경과 더불어 @Controller 어노테이션을 통해
선언한 컨트롤러도 함께 사용할 수 있도록 확장하였다. 이와 같이 버전을 업그레이드하면서 하위 버전을 충분히 지원해주기 위해선 잘 설계된 인터페이스가 정말 중요하다. 
스프링 프레임워크가 바로 그 대표적인 예시이며, 그러한 의미에서 우리가 만든 DI 프레임워크도 충분한 확장성을 갖출 수 있도록 인터페이스화하는 작업을 진행해볼 필요가 있다.  
그 과정에서 인터페이스 기반 개발의 장점을 직접 느껴보고 객체지향 설계에 대해 다시 한번 고민해볼 수 있는 시간을 가져보자.

# 리팩토링
1. 각 기능마다 변화가 발생하는 시점이 다른 부분은 서로 다른 인터페이스로 분리함으로써, 각 인터페이스마다 서로 다른 템포로 변화, 발전할 수 있게 되며 이는 곧 프레임워크가 유연해졌다는 것을 의미한다.
    ```java
    package com.framework.core.di.beans.factory.support;
    
    import com.framework.core.di.DefaultBeanDefinition;
        
    public interface BeanDefinitionRegistry {
        void registerBeanDefinition(Class<?> clazz, DefaultBeanDefinition beanDefinition);
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

    import com.framework.core.di.beans.factory.support.InjectType;
    
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
2. 각 인터페이스 사이의 연결은 구현 클래스가 아닌 인터페이스를 통해서만 의존관계를 가지도록 함으로써 유연한 구조를 가져갈 수 있다. 이로 인해 
   각각의 인터페이스는 서로 다른 속도로 변화해 나가는 것이 가능하고, 그 덕분에 각자의 속도로 서로 다르게 변화하는 구현 클래스 사이의 연결은 
   팩토리 클래스가 담당하도록 구현함으로써 개발자의 편의성을 더욱 높일 수 있게 된다. 지금까지의 구현 과정에서 우리가 사용한 팩토리 클래스는 `ApplicationContext`였다.
   이 팩토리 클래스 또한 마찬가지로 구현 클래스의 연결 조합에 따라 다양한 조합이 가능하기 때문에 인터페이스를 추가한 후 확장 가능하도록 구현 할 수 있다.
   ```java
   package com.framework.core.di.context;
   
   import java.util.Set;
   
   public interface ApplicationContext {
      <T> T getBean(Class<T> clazz);
      Set<Class<?>> getBeanClasses();
   }
   ```
   `AnnotationConfigApplicationContext`는 각 인터페이스에 대한 구현체를 조합하는 역할만 담당하고, 애노테이션 설정 기반의 DI 컨테이너에 대한 실질적인 작업은 
   `BeanFactory`에 모두 위임하고 있다. 같은 방식으로 XML 설정 기반의 DI 컨테이너를 만들고 싶으면 XML설정을 읽어 `BeanDefinition`으로 생성하는 
   `XmlBeanDefinitionReader`를 추가하고, 이 구현 클래스들을 조합해 XML 설정 기반의 DI 컨테이너를 만드는 `XmlApplicationContext`를 구현하면 된다.
   실제로 스프링에서 XML 기반으로 설정파일을 읽어와 구현체들을 조합하는 `XmlWebApplicationContext`의 코드를 보면 이해에 도움이 될 것이다.
   ```java
   public class XmlWebApplicationContext extends AbstractRefreshableWebApplicationContext {
       public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/applicationContext.xml";
       public static final String DEFAULT_CONFIG_LOCATION_PREFIX = "/WEB-INF/";
       public static final String DEFAULT_CONFIG_LOCATION_SUFFIX = ".xml";
   
       public XmlWebApplicationContext() {
       }
   
       protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
           XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
           beanDefinitionReader.setEnvironment(this.getEnvironment());
           beanDefinitionReader.setResourceLoader(this);
           beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));
           this.initBeanDefinitionReader(beanDefinitionReader);
           this.loadBeanDefinitions(beanDefinitionReader);
       }
   
       protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
       }
   
       protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws IOException {
           String[] configLocations = this.getConfigLocations();
           if (configLocations != null) {
               String[] var3 = configLocations;
               int var4 = configLocations.length;
   
               for(int var5 = 0; var5 < var4; ++var5) {
                   String configLocation = var3[var5];
                   reader.loadBeanDefinitions(configLocation);
               }
           }
   
       }
   
       protected String[] getDefaultConfigLocations() {
           return this.getNamespace() != null ? new String[]{"/WEB-INF/" + this.getNamespace() + ".xml"} : new String[]{"/WEB-INF/applicationContext.xml"};
       }
   }
   ```
3. 객체지향 프로그래밍에서 중복코드 제거하기.
   * 객체지향 개발에서 중복 코드를 제거하는 방법은 상속을 통한 방법과 조합(Composite)을 통한 방법이 대표적이다. 지금까지 학습한 DI가 조합을 이용한 방법이었고,
     일반적으로 변화에 유연하게 대처하기 위해, 상속보다는 조합을 이용하여 클래스 간의 의존관계가 느슨할 수 있도록 구현하는 것이 권장되는 방법이다.
     실습 코드를 다시 생각해보면 앞에서 작성한 `AnnotationConfigApplicationContext` 구현의 경우 `DefaultBeanFactory`를 상속할 수도 있다. 하지만 그렇게 될 경우, 
     강한 의존관계(tightly coupling)를 가지게 되어, `DefaultBeanFactory`와 `AnnotationConfigApplicationContext`의 변화 속도가 같아질 수 밖에 없다.
     상속대신 조합을 선택함으로써 각자 클래스의 페이스에 맞게 변화에 대응할 수 있어진 것이다. 또한 조합을 통해 의존관계를 가지더라도 구현클래스에 의존하는 것보다 인터페이스에 의존하는 것이 더 유연한 구조일 것이다. 
     애플리케이션이 유연하다는 것은 테스트하기도 쉬워진다는 것을 의미이기 때문에, 급변하는 비즈니스 흐름 속에서 보다 안정적으로 애플리케이션을 설계하고 리팩토링하는 것이 가능해진다.
   * 인터페이스 기반 개발과 DI를 활용해 의존관계를 연결함으로써 애플리케이션의 유연성을 극대화할 수 있다. 유연성을 극대화하는 점은 좋지만 클래스를 사용하는 개발자 입장에서 
     클래스간의 의존관계를 파악해 매번 DI를 하는 것은 불편할 뿐만 아니라, 의존관계를 잘못 연결하는 경우 문제가 발생할 가능성도 크다. 그래서 좀 더 쉽고 안전하게 객체들의 관계를 자동으로
     연결해주는 DI 컨테이너가 존재하는 것이다. 
  
4. 애플리케이션 개발
   * 지금까지 구현한 DI 프레임워크는 모든 곳에서 사용할 수 있는 범용성 있는 코드를 목표로 했기 때문에 인터페이스를 통한 유연성과 확장성에 초점을 맞췄다.
     하지만 우리가 흔히 개발하는 애플리케이션의 경우 이정도 수준의 유연성과 확장성이 필요한 경우는 거의 없고, 애플리케이션 자체에서만 호라용해도 충분한 코드가 대부분이다.
     따라서 무조건 인터페이스만이 정답이다라는 생각보단 자신의 코드는 유연성이 필요한 부분이 어디인지를 고민하여 상황에 맞춰 인터페이스를 도입하는 것이 중요할 것이다. 
     
   