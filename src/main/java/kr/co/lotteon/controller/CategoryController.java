package kr.co.lotteon.controller;

import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final ProductService productService;

    @GetMapping("/test")
    public ResponseEntity<?> getCategoryList() {
        log.info(productService.getCategoryList().toString());
        List<CategoryResult> cateList = productService.getCategoryList();

        return ResponseEntity.ok(productService.getCategoryList());
    }
}
