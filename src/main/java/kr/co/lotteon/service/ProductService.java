package kr.co.lotteon.service;

import kr.co.lotteon.entity.Product;
import kr.co.lotteon.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;


    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findByPname(Pageable pageable,String name){

        return productRepository.findByPnameLike(pageable, "%"+name+"%");
    }

    public Optional<Product> findProductById(int productId) {
        return productRepository.findById(productId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CategoryResult> getCategoryList() {
        List<CategoryResult> results = categoryRepository.findAll().stream().map(CategoryResult::of).collect(Collectors.toList());
        log.info(results.toString());
        return results;
    }
}


