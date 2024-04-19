package kr.co.lotteon.dto;

import kr.co.lotteon.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private int cate;
    private String cname;
    private List<CategoryDTO> children;

    public static CategoryDTO of(Category category) {
        return new CategoryDTO(
                category.getCate(),
                category.getCname(),
                category.getChildren().stream().map(CategoryDTO::of).collect(Collectors.toList())
        );
    }
}
