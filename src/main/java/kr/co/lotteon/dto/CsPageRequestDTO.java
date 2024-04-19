package kr.co.lotteon.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsPageRequestDTO {

    @Builder.Default
    private int pg=1;

    @Builder.Default
    private int size=10;

    @Builder.Default
    private int cate1 = 0;
    @Builder.Default
    private int cate2 = 0;
    private int type;

    @Builder.Default
    private String search = "";

    public Pageable getPageable(String sort){
        return PageRequest.of(
                this.pg - 1,
                this.size,
                Sort.by(sort).descending()
        );
    }
}