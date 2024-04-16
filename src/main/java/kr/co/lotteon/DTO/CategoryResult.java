package kr.co.lotteon.DTO;

import kr.co.lotteon.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResult {
    private int cate;
    private String cname;
    private List<CategoryResult> children;

    public static CategoryResult of(Category category) {
        return new CategoryResult(
                category.getCate(),
                category.getCname(),
                category.getChildren().stream().map(CategoryResult::of).collect(Collectors.toList())
        );
    }
}
