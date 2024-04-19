package kr.co.lotteon.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    /*
       build.gradle 설정 후 JPAQueryFactory 빈 설정을 해야 에러가 나지 않음
   */
    // JPA에서 엔티티 매니저를 주입받기 위한 어노테이션
    @PersistenceContext
    private EntityManager entityManager;


    // JPAQueryFactory 빈을 생성하는 메서드

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        // JPA 엔티티 매니저를 사용하여 JPAQueryFactory 인스턴스를 생성하고 반환
        // 이렇게 함으로써 QueryDSL을 사용하여 JPA 쿼리를 생성할 수 있다.
        return new JPAQueryFactory(entityManager);
    }
}
