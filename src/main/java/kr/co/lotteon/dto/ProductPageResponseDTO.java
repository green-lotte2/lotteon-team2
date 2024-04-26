package kr.co.lotteon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Setter
@Data
public class ProductPageResponseDTO {
    private List<NoticeDTO> noticeList;
    private List<FaqDTO> faqList;
    private List<QnaDTO> qnaList;
    // 상품검색 결과
    private List<ProductDTO> dtoList;

    private int cate;
    private int pg;
    private int size;
    private int total;
    private int totalPage;
    private String search;

    private int start, end;
    private boolean prev, next;

    @Builder
    public ProductPageResponseDTO(List<NoticeDTO> noticeList, int total,
                                  List<FaqDTO> faqList, List<QnaDTO> qnaList, List<ProductDTO> dtoList,
                                  int pg, int size, int cate, String search) {
        this.pg = pg;
        this.size = size;
        this.total = total;
        this.cate = cate;
        this.totalPage = (int) Math.ceil((double) total / this.size);
        this.search = search;

        this.noticeList = noticeList;
        this.faqList = faqList;
        this.qnaList = qnaList;
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil((double) this.pg / 10.0)) * 10;
        this.end = Math.min(this.end, this.totalPage);
        this.start = this.end - 9;
        this.start = Math.max(this.start, 1);

        this.end = this.end == 0 ? 1 : this.end;
        this.prev = this.start > 1;
        this.next = this.pg < this.totalPage;
    }
    }