package kr.co.lotteon.repository.impl;


import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.QUser;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;
    private QUser qUser = QUser.user;


    @Override
    public Page<Tuple> adminSelectUsers(PageRequestDTO pageRequestDTO, Pageable pageable) {
        // 전체 결과 수 가져오기
        long total = jpaQueryFactory
                .selectFrom(qUser)
                .fetchCount();
        log.info("total :" + total);

        // 페이징 처리를 위해 offset과 limit 설정
        List<Tuple> results = jpaQueryFactory
                .select(qUser, qUser.uid)
                .from(qUser)
                .orderBy(qUser.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("selectArticles...1-2 : " + results);


        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Tuple> adminSearchUsers(PageRequestDTO pageRequestDTO, Pageable pageable) {
        // 전체 결과 수 가져오기
        long total = jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.uid.contains(pageRequestDTO.getKeyword()).and(qUser.role.eq(pageRequestDTO.getRole())))
                .fetchCount();
        log.info("total :" + total);

        // 페이징 처리를 위해 offset과 limit 설정
        List<Tuple> results = jpaQueryFactory
                .select(qUser, qUser.uid)
                .from(qUser)
                .where(qUser.uid.contains(pageRequestDTO.getKeyword()).and(qUser.role.eq(pageRequestDTO.getRole())))
                .orderBy(qUser.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("selectArticles...1-2 : " + results);


        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(results, pageable, total);

    }

}
