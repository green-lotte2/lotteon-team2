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
    public List<UserDTO> adminSearchUsers(PageRequestDTO pageRequestDTO, Pageable pageable, String role, String keyword) {

        return null;
    }


/*

    @Override
    public Page<User> adminSearchUsers(PageRequestDTO pageRequestDTO, Pageable pageable) {


        String role = pageRequestDTO.getRole();
        String keyword = pageRequestDTO.getKeyword();

        // 검색 종류에 따른 where 표현식 생성
        BooleanExpression expression = null;
        long total = 0;
        List<User> users = null;
        if (role.equals("role")) {
            expression = qUser.role.eq(role).and(qUser.uid.contains(keyword));
            log.info("expression : " + expression);


            // 전체 결과 수 가져오기
           total = jpaQueryFactory
                    .selectFrom(qUser)
                    .where(expression)
                    .fetchCount();
            log.info("total :" + total);

            // 검색 조건을 적용하여 사용자 목록 가져오기
            users = jpaQueryFactory
                    .select(qUser)
                    .from(qUser)
                    .where(expression)
                    .orderBy(qUser.regDate.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }


            // 페이징 처리를 위해 page 객체 리턴
            return new PageImpl<>(users, pageable, total);
        }
*/
    }
