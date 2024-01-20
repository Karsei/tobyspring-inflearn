![jdbc_autoconfiguration.png](images%2Fjdbc_autoconfiguration.png)

`@ConditionalOnMissingBean`, `@ConditionalMyOnClass` 이용해서 `DataSource` 판별

```groovy
dependencies {
    implementation 'org.springframework:spring-jdbc'
    implementation 'com.zaxxer:HikariCP:5.1.0'
    runtimeOnly 'com.h2database:h2:2.2.220' // 직접 이용하는 경우는 없음
}
```

```java
@MyAutoConfiguration
@ConditionalMyOnClass("org.springframework.jdbc.core.JdbcOperations")
@EnableMyConfigurationProperties(MyDataSourceProperties.class)
public class DataSourceConfig {
    @Bean
    @ConditionalMyOnClass("com.zaxxer.hikari.HikariDataSource")
    @ConditionalOnMissingBean
    DataSource hikariDataSource(MyDataSourceProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    DataSource dataSource(MyDataSourceProperties properties) throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(properties.getDriverClassName()));
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        return dataSource;
    }
```

상단에 `@EnableTransactionManagement` 있을 경우 `@Transactional` 사용 가능하게 됨

이때, `JdbcTranscationManager` 가 있어야 하며 대체적으로 `PlatformTransactionManager` 를 이용함

```java
@EnableTransactionManagement // TransactionManagementConfigurationSelector
public class DataSourceConfig {
    @Bean
    @ConditionalOnSingleCandidate(DataSource.class) // Spring Bean 구성정보 중 DataSource 가 딱 하나만 등록되어 있을 때만 사용하곘다는 의미
    @ConditionalOnMissingBean
    JdbcTransactionManager jdbcTransactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
```

`DataSource` 를 테스트할 때는 아래와 같이 Spring 구성 정보를 읽어들이는 컨테이너 Annotation 과 함께 주면 간단하게 테스트 가능

```java
@ExtendWith(SpringExtension.class) // Spring Context 를 이용한 컨테이너 테스트 가능
@ContextConfiguration(classes = TobyspringInflearnApplication.class) // 모든 bEan 구성 정보를 끌어오는 시작점이 되는 클래스 -> Import, ComponentScan 을 통한 Bean 구성 가져오기 가능
@TestPropertySource("classpath:/application.properties") // application.properties 등록은 Spring Framework 가 아닌 Spring Boot 에서 동작되는 것이므로 따로 테스트에서 불러오도록 추가해야 함
@Transactional
public class DataSourceTest {
    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.close();
    }
}
```

클래스에 붙는 Annotation 들이 너무 많으므로 재사용을 쉽게 가능하도록 하나의 Annotation 으로 분리

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(SpringExtension.class) // Spring Context 를 이용한 컨테이너 테스트 가능
@ContextConfiguration(classes = TobyspringInflearnApplication.class) // 모든 bEan 구성 정보를 끌어오는 시작점이 되는 클래스 -> Import, ComponentScan 을 통한 Bean 구성 가져오기 가능
@TestPropertySource("classpath:/application.properties") // application.properties 등록은 Spring Framework 가 아닌 Spring Boot 에서 동작되는 것이므로 따로 테스트에서 불러오도록 추가해야 함
@Transactional
public @interface HelloBootTest {
}
```

```java
@HelloBootTest
public class DataSourceTest {
    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.close();
    }
}
```