package kr.co.lotteon.dto;
import lombok.*;
import org.springframework.data.domain.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductPageRequestDTO {

    @Builder.Default
    private int pg=1;
    @Builder.Default
    private int size=10;

    private String beginDate; // 날짜 필터링 시작 날짜
    private String endDate; // 날짜 필터링 종료 날짜

    @Builder.Default
    private int cate1 = 0;
    @Builder.Default
    private int cate2 = 0;


    @Builder.Default
    private String search = "";

    private Integer minPrice;
    private Integer maxPrice;
    private int pno;

    private int ono;

    private String type;
    private String keyword;

    public Pageable getPageable(String sort){
        return PageRequest.of(
                this.pg - 1,
                this.size,
                Sort.by(sort).descending()
        );
    }

    //🎈 상품검색

    private String company;
    private String seller;
    private String pname;
    @Builder.Default
    private int cate = 0;
}