package kr.co.lotteon.service;

import kr.co.lotteon.entity.Product;
import kr.co.lotteon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class ProductService {

        private final ProductRepository productRepository;

        @Autowired
        public ProductService(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        public Page<Product> findAllProducts(Pageable pageable) {
            return productRepository.findAll(pageable);
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

    }
