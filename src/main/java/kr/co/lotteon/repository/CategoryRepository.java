package kr.co.lotteon.repository;

import jakarta.persistence.EntityManager;
import kr.co.lotteon.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;

    public List<Category> findAll() {
        return em.createQuery("select c from Category c where c.parent is NULL", Category.class).getResultList();
    }
}
