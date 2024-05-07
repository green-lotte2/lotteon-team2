package kr.co.lotteon.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.entity.QProduct;
import kr.co.lotteon.entity.QProductimg;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
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
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QProduct qProduct = QProduct.product;
    private QProductimg qProductimg = QProductimg.productimg;

    public Page<Tuple> selectProducts(PageRequestDTO pageRequestDTO, Pageable pageable) {

        int pno = pageRequestDTO.getPno();

        log.info("selectProducts... : " + pno);

        // 부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qProduct, qProductimg.mainimg)
                .from(qProduct)
                .where(qProduct.pno.eq(pno))
                .join(qProductimg)
                .on(qProduct.pno.eq(qProductimg.pno))
                .orderBy(qProduct.rdate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        log.info("selectProducts...1-2 : " + results);

        List<Tuple> content = results.getResults();
        
        log.info("selectrProducts...1-3 : " + content);

        long total = results.getTotal();
        
        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);

    }

    @Override
    public Page<Tuple> searchProducts(PageRequestDTO pageRequestDTO, Pageable pageable) {

        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        int cate = pageRequestDTO.getCate();

        // 검색 종류에 따른 where 표현식 생성
        /*
        BooleanExpression expression = null;

        if(type.equals("company")) {
            expression = qProduct.company.eq(cate).and(qProduct.company.contains(keyword));
            log.info("expression : " + expression);

        }else if(type.equals("seller")) {
            expression = qProduct.company.eq(cate).and(qArticle.content.contains(keyword));
            log.info("expression : " + expression);

        }else if(type.equals("pname")) {
            com.querydsl.core.types.dsl.BooleanExpression titleContains = qProduct.title.contains(keyword);
            com.querydsl.core.types.dsl.BooleanExpression contentContains = qProduct.content.contains(keyword);
            expression = qArticle.cate.eq(cate).and(titleContains.or(contentContains));
            log.info("expression : " + expression);

        }
*/
        return null;


    }


}
