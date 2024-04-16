package kr.co.lotteon.service;

import kr.co.lotteon.DTO.CategoryResult;
import kr.co.lotteon.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public List<CategoryResult> getCategoryList() {
        List<CategoryResult> results = categoryRepository.findAll().stream().map(CategoryResult::of).collect(Collectors.toList());
        log.info(results.toString());
        return results;
    }
}
