package kr.co.lotteon.dto;
import lombok.*;
import org.springframework.data.domain.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PageRequestDTO {

    @Builder.Default
    private int pg=1;

    @Builder.Default
    private int size=10;

    @Builder.Default
    private int cate1 = 0;
    @Builder.Default
    private int cate2 = 0;


    @Builder.Default
    private String search = "";

    private int pno;

    private int ono;

    public Pageable getPageable(String sort){
        return PageRequest.of(
                this.pg - 1,
                this.size,
                Sort.by(sort).descending()
        );
    }

    //🎈 상품검색
    private String type;
    private String keyword;

    private String company;
    private String seller;
    private String pname;
    private String cate;
}