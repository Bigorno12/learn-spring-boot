# Spring and Spring Boot Framework

***
## What is Spring Container?

- **Spring Container**: Manages Spring beans and their life cycle.

1. **BeanFactory**: Basic Spring Container

2. **Application Context**: Advanced Spring Container with enterprise-specific features.
    > - Easy to use web application context: **AnnotationConfigWebApplicationContext**
    > - Easy internationalization: **ResourceBundleMessageSource**
    > - Easy integration with Spring AOP

- **Which one to use**: Most enterprise applications use **Application Context**.
    - Recommended for web application, web services, REST API and microservices.

***
## Exploring JAVA Bean V/S POJO V/S Spring Bean

- **Java Bean**: Classes adhering to 3 contraints:
  > - Should have a no-arg constructor
  > - Should be Serializable
  > - Should have getter and setter methods for properties

- **POJO**: Plain Old Java Object
    > - No constraints
    > - Any Java Object is a POJO!

- **Spring Bean**: Java Object that is managed by Spring
    > - Spring uses IOC Container (Bean Factory or Application Context) to manage these objects

***
## @component vs @service vs @repository vs @configuration vs @bean

| **Annotation** | **Description**                                                                                                                                                                                                                          | **When to Use**                                                                                                               |
|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| @Component     | A generic stereotype for any spring managed component. It is used across the application to mark the beans as Spring's managed components                                                                                                | Use it when you want to indicate that a class is a spring component.                                                          |
| @Service       | Annotates classes at the service layer. It is used to indicate that the class is holding the business logic                                                                                                                              | Use it when you have a class that holds business logic                                                                        |
| @Repository    | Annotates classes at the persistence layer, which will act as a database repository. It's job is to catch persistence-specific exceptions and re-throw them as one of Spring's unified unchecked exceptions                              | Use it when you have a class that interacts with the database                                                                 |
| @Configuration | Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime. It's used to manually define beans                  | Use it when you want to manually define beans or when you need to perform some computations or logic before creating the bean |
| @Bean          | Tells Spring that a method with @Bean will return an object that should be registered as a bean in the Spring application context. It's a method-level annotation and has a used within the class which is annotated with @Configuration | Use it when you want to create a bean to be managed by the Spring container                                                   |

***
## @Primary vs @Qualifier

| **Annotation** | **When to Use**                                                                  |
|----------------|----------------------------------------------------------------------------------|
| @Primary       | A bean should be given preference when multiple candidates are qualified         |
| @Qualifier     | A specific bean should be auto-wired (name of the bean can be used as qualifier) |

```
@Component 
@Primary
class QuickSort implement SortingAlgorithm {

}

@Component
class BubbleSort implement SortingAlgorithm {

}


@Component 
@Qualifier("RadixSortQualifier")
class RadixSort implement SortingAlgorithm {

}


@Component
class ComplexAlgorithm {

@Autowired
private SortingAlgorithm algorithm;

}
@Component
class AnotherComplexAlgorithm {

@Autowired @Qualifier("RadixSortQualifier")
private SortingAlgorithm iWantToUseRadixSortOnly;

}
```

- **ALWAYS** think from the perspective of the class using the SortingAlgorithm:
    > - Just @Autowired: Give me (preferred) SortingAlgorithm
    > - @Autowired + @Qualifier: I only want to use specific SortingAlgorithm - RadixSort
    > - **(REMEMBER) @Qualifier has higher priority then @Primary**

***
## Exploring Spring - Dependency Injection Types

- **Constructor-based** : Dependencies are set by creating the Bean using its Constructor

- **Setter-based** : Dependencies are set by calling setter methods on your beans

- **Field**: No setter or constructor. Dependency is injected using reflection.

- **Question: Which one should you use?**
    > - Spring team recommends Constructor-based injection as dependencies are automatically set when an object is
      created!
    > - Why -> All the initialization happens in one method then the bean is ready to use! and for testing it is easy to
      mock the dependencies.
***
## Spring Framework - Important Terminology

- **@Component**: An instance of class will be managed by Spring framework

- **Dependency**: GameRunner needs GamingConsole impl!
    > - GamingConsole Impl (Ex: MarioGame) is a dependency of GameRunner

- **Component Scan**: How does Spring Framework find component classes?
    > - It scans packages! (@ComponentScan("com.in28minutes"))

- **Dependency Injection**: Identify beans, their dependencies and wire them together (provides IOC - Inversion of
  Control)
    > - **Spring Beans**: An object managed by Spring Framework
    > - **IoC container**: Manages the lifecycle of beans and dependencies
        > - **Types**: ApplicationContext (complex), BeanFactory (simpler features - rarely used)
        > - **Autowiring**: Process of wiring in dependencies for a Spring Bean
***
## @Component vs @Bean

| **Heading**                | **@Component**                                                    | **@Bean**                                                                               |
|----------------------------|-------------------------------------------------------------------|-----------------------------------------------------------------------------------------|
| **Where?**                 | Can be used on any Java class Typically used on methods in Spring | Typically used on methods in Spring.<br/> Configuration classes                         |
| **Ease of Use**            | Very easy. Just add an annotation.                                | You write all the code.                                                                 |
| **Autowiring**             | Yes - Field, Setter or Constructor Injection                      | Yes - method call or method parameters                                                  |
| **Who Creates the beans?** | Spring Framework                                                  | You write bean creation code                                                            |
| **Recommended For**        | Instantiating Beans for Your Own Application<br/>Code: @Component | 1. Custom Business Logic<br/>2. Instantiating Beans for 3rd-party<br/> libraries: @Bean |
| **Beans per class**        | One (Singleton) or Many (Prototype)                               | One or Many - You can create as many as you want                                        |

***
## Exploring Lazy Initialization of Spring Beans

- Default initialization for Spring Beans: **Eager**

- Eager initialization is recommended:
    > - Errors in the configuration are discovered immediately at application startup

- However, you can configure beans to be lazily initialized using **Lazy** annotation:
    > - NOT recommended (AND) Not frequently used

- **Lazy** annotation:
    > - Can be used almost everywhere @Component and @Bean are used
    > - Lazy-resolution proxy will be injected instead of actual dependency
    > - Can be used on Configuration (@Configuration) class:
        > - All @Bean methods within the @Configuration will be lazily initialized
    > - If error occur will not be displayed at startup but at the time of bean creation therefore at runtime.

***
## Comparing Lazy Initialization vs Eager Initialization

| **Heading**                                           | **Lazy initialization**                                          | **Eager initialization**                         |
|-------------------------------------------------------|------------------------------------------------------------------|--------------------------------------------------|
| **Initialization time**                               | Bean initialized when it is first made use of in the application | Bean initialized at startup of the application   |
| **Default**                                           | NOT Default                                                      | Default                                          |
| **Code Snippet**                                      | @Lazy OR @Lazy(value=true)                                       | @Lazy(value=false) OR (Absence of @Lazy)         |
| **What happens if there are errors in initializing?** | Errors will result in runtime exceptions                         | Errors will prevent application from starting up |
| **Usage**                                             | Rarely used                                                      | Very frequently used                             |
| **Memory Consumption**                                | Less (until bean is initialized)                                 | All beans are initialized at startup             |
| **Recommended Scenario**                              | Beans very rarely used in your app                               | Most of your beans                               |

***
## Spring Bean Scopes

- Spring Beans are defined to be used in a specific scope:
    > - **Singleton**: One object instance per Spring IoC container

    > - **Prototype**: Possibly many object instances per Spring IoC container

    > - Scopes applicable ONLY for web-aware Spring ApplicationContext
        > - **Request**: One object instance per single HTTP request
        > - **Session**: One object instance per user HTTP Session
        > - **Application**: One object instance per web application runtime
        > - **Websocket**: One object instance per WebSocket instance

- **Java Singleton (GOF) vs Spring Singleton**
    > - **Spring Singleton**: One object instance per Spring IoC container
    > - **Java Singleton (GOF)**: One object instance per JVM

***
## Prototype vs Singleton Bean Scope

| **Heading**              | **Prototype**                                                | **Singleton**                                                    |
|--------------------------|--------------------------------------------------------------|------------------------------------------------------------------|
| **Instances**            | Possibly Many per Spring IOC Container                       | One per Spring IOC Container                                     |
| **Beans**                | New bean instance created every time the bean is referred to | Same bean instance reused                                        |
| **Default**              | NOT Default                                                  | Default                                                          |
| **Code Snippet**         | @Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)       | @Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON OR Default |
| **Usage**                | Rarely used                                                  | Very frequently used                                             |
| **Recommended Scenario** | Stateful beans                                               | Stateless beans                                                  |

***
## Let's Compare: Annotations vs XML Configuration

| **Heading**              | **Annotations**                                                     | **XML Configuration**        |
|--------------------------|---------------------------------------------------------------------|------------------------------|
| **Ease of Use**          | Very Easy (defined close to source - class, method and/or variable) | Cumbersome                   |
| **Short and Concise**    | Yes                                                                 | No                           |
| **Clean POJOs**          | No. POJOs are polluted with Spring Annotations                      | Yes. No change in Java code. |
| **Easy to Maintain**     | Yes                                                                 | No                           |
| **Usage Frequency**      | Almost all recent projects                                          | Rarely                       |
| **Recommendation**       | Either of them is fine BUT be consistent                            | Do NOT mix both              |
| **Debugging difficulty** | Hard                                                                | Medium                       |

***
## Spring Stereotype Annotations - @Component & more..

- **@Component** - Generic annotation applicable for any class
    > - **Base** for all Spring Stereotype Annotations
    > - **Specializations** of @Component:
        > - **@Service** - Indicates that an annotated class has business logic
        > - **@Controller** - Indicates that an annotated class is a "Controller" (e.g. a web controller)
            > - Used to define controllers in your web applications and REST API
        > - **@Repository** - Indicates that an annotated class is used to retrieve and/or manipulate data in a database
- **What should you use?**
    > - **(MY RECOMMENDATION)** Use the most specific annotation possible
    > - **Why?**
        > - By using a specific annotation, you are giving more information to the framework about your intentions.
        > - You can use AOP at a later point to add additional behavior
            > - Example: For @Repository, Spring automatically wires in JDBC Exception translation features

***
## Quick Review of Important Spring Annotations

| **Annotation**                                               | **Description**                                                                                                                                                                                                 |
|--------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **@Configuration**                                           | Indicates that a class declares one or more @Bean methods and may be processed by the Spring container to generate bean definitions                                                                             |
| **@ComponentScan**                                           | Define specific packages to scan for components. If specific packages are not defined, scanning will occur from the package of the class that declares this annotation                                          |
| **@Bean**                                                    | Indicates that a method produces a bean to be managed by the Spring container                                                                                                                                   |
| **@Component**                                               | Indicates that an annotated class is a "component"                                                                                                                                                              |
| **@Service**                                                 | Specialization of @Component indicating that an annotated class has business logic                                                                                                                              |
| **@Controller**                                              | Specialization of @Component indicating that an annotated class is a "Controller" (e.g. a web controller). Used to define controllers in your web applications and REST API                                     |
| **@Repository**                                              | Specialization of @Component indicating that an annotated class is used to retrieve and/or manipulate data in a database                                                                                        |
| **@Primary**                                                 | Indicates that a bean should be given preference when multiple candidates are qualified to autowire a single valued dependency                                                                                  |
| **@Qualifier**                                               | Used on a field or parameter as a qualifier for candidate beans when autowiring                                                                                                                                 |
| **@Lazy**                                                    | Indicates that a bean has to be lazily initialized. Absence of @Lazy annotation will lead to eager initialization.                                                                                              |
| **@Scope (value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)** | Defines a bean to be a prototype - a new instance will be created every time you refer to the bean. Default scope is singleton - one instance per IOC container.                                                |
| **@PostConstruct**                                           | Identifies the method that will be executed aft er dependency injection is done to perform any initialization                                                                                                   |
| **@PreDestroy**                                              | Identifies the method that will receive the callback notification to sigal that the instance is in the process of being removed by the container. Typically used to release resources that it has been holding. |
| **@Named**                                                   | Jakarta Contexts & Dependency Injection (CDI) Annotation similar to Component                                                                                                                                   |
| **@Inject**                                                  | Jakarta Contexts & Dependency Injection (CDI) Annotation similar to Autowired                                                                                                                                   |

***
## Quick Review of Important Spring Concepts

| **Concept**              | **Description**                                                                                                                                                           |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Dependency Injection** | Identify beans, their dependencies and wire them together (provides IOC - Inversion of Control)                                                                           |
| **Constr. injection**    | Dependencies are set by creating the Bean using its Constructor                                                                                                           |
| **Setter injection**     | Dependencies are set by calling setter methods on your beans                                                                                                              |
| **Field injection*       | * No setter or constructor. Dependency is injected using reflection.                                                                                                      |
| **IOC Container**        | Spring IOC Context that manages Spring beans & their lifecycle                                                                                                            |
| **Bean Factory**         | Basic Spring IOC Container                                                                                                                                                |
| **Application Context**  | Advanced Spring IOC Container with enterprise-specific features - Easy to use in web applications with internationalization features and good integration with Spring AOP |
| **Spring Beans**         | Objects managed by Spring                                                                                                                                                 |

***