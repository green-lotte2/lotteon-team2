package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.QOrderDetail;
import kr.co.lotteon.entity.QOrders;
import kr.co.lotteon.entity.QProduct;
import kr.co.lotteon.entity.QProductimg;
import kr.co.lotteon.repository.custom.OrderRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QOrders qOrders = QOrders.orders;
    private QOrderDetail qOrderDetail = QOrderDetail.orderDetail;
    private QProductimg qProductimg = QProductimg.productimg;
    private QProduct qProduct = QProduct.product;

    @Override
    public Page<Tuple> selectOrders(PageRequestDTO pageRequestDTO, Pageable pageable, String uid) {
        BooleanExpression condition = qOrders.uid.eq(uid); // 사용자 ID를 기준으로 필터링

        List<Tuple> results = jpaQueryFactory
                .select(qOrders,
                        qOrderDetail.pno,
                        qProduct.cate,
                        qProduct.pname,
                        qProduct.company,
                        qProductimg.mainimg,
                        qOrderDetail.pcount,
                        qOrderDetail.options,
                        qOrderDetail.price)
                .from(qOrders)
                .join(qOrderDetail).on(qOrders.ono.eq(qOrderDetail.ono))
                .join(qProduct).on(qOrderDetail.pno.eq(qProduct.pno))
                .join(qProductimg).on(qProduct.pno.eq(qProductimg.pno))
                .where(condition)
                .orderBy(qOrders.odate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("results1111111111111" + results);
       long total = jpaQueryFactory
                .select(qOrders.count())
                .from(qOrders)
                .join(qOrderDetail).on(qOrders.ono.eq(qOrderDetail.ono))
                .join(qProduct).on(qOrderDetail.pno.eq(qProduct.pno))
                .where(condition)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
