package kr.co.lotteon.repository;

import kr.co.lotteon.entity.Product;
import kr.co.lotteon.repository.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryCustom {
    public Page<Product> findByPnameLike(Pageable pageable, String name);

    public Page<Product> findByCateBetween(Pageable pageable, int cate, int depth);

    public List<Product> findTop8ByOrderByRdateDesc();

    Page<Product> findByCate(int cate, Pageable pageable);

    Page<Product> findAllByUid(String uid, Pageable pageable);



}
