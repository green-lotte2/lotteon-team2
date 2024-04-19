package kr.co.lotteon.dto;


import lombok.*;
import org.springframework.data.domain.Page;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductPageResponseDTO<DTO, EN> {

    private List<DTO> dtoList;
    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;

    public ProductPageResponseDTO(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
        this.totalPage = result.getTotalPages();
        this.page = result.getNumber() + 1;
        this.size = result.getSize();
        this.end = Math.min((int) (Math.ceil(page / 10.0)) * 10, totalPage);
        this.start = end - 9;
        this.prev = start > 1;
        this.next = totalPage > end;
    }
}