package kr.co.lotteon.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPageRequestDTO {
    @Builder.Default
    private int pg = 1; // Default page number is 1
    @Builder.Default
    private int size = 10; // Default page size is 10
    private String sortProperty = "id"; // Default sort property

    public Pageable getPageable() {
        return PageRequest.of(this.pg - 1, this.size, Sort.by(this.sortProperty).descending());
    }
}