package kr.co.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageRequestDTO {

    @Builder.Default
    private int pg = 1;
    @Builder.Default
    private int size = 10;
    @Builder.Default
    private String search = "";

    //🎈 상품검색 특화 필드
    private String type;
    private String keyword;
    private String company;
    private String seller;
    private String pname;
    private String cate;

    public Pageable getPageable(String sort){
        return PageRequest.of(
                this.pg - 1,
                this.size,
                Sort.by(sort).descending()
        );
    }
}