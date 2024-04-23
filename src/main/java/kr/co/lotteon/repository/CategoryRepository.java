package kr.co.lotteon.repository;

import jakarta.persistence.EntityManager;
import kr.co.lotteon.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;

    public List<Category> findAll() {
        return em.createQuery("select c from Category c where c.parent is NULL", Category.class).getResultList();
    }

    // 사용자가 입력한 카테고리 이름으로 카테고리를 검색하는 메서드
    public List<Category> findByCname(String cname) {
        return em.createQuery("select c from Category c where c.cname like :cname", Category.class)
                .setParameter("cname", '%' + cname + '%')
                .getResultList();
    }

    public Optional<Category> findById(int categoryId) {
        return em.createQuery("SELECT c FROM Category c WHERE c.cate = :cate", Category.class)
                .setParameter("cate", categoryId)
                .getResultList()
                .stream()
                .findFirst();
    }


}
