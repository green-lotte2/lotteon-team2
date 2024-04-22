package kr.co.lotteon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class PageResponseDTO {
    private Optional<List<NoticeDTO>> noticeList;
    private Optional<List<FaqDTO>> faqList;
    private Optional<List<QnaDTO>> qnaList;
    // 상품검색
    private Optional<List<ProductDTO>> dtoList;

    private int pg;
    private int size;
    private int total;
    private int totalPage;
    private int cate1;
    private int cate2;
    private String search;

    private int start, end;
    private boolean prev, next;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO,
                           List<NoticeDTO> noticeList, int total,
                           List<FaqDTO> faqList, List<QnaDTO> qnaList, List<ProductDTO> dtoList) {

        this.cate1 = pageRequestDTO.getCate1();
        this.cate2 = pageRequestDTO.getCate2();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.totalPage = (int) Math.ceil((double) total / this.size);
        this.search = pageRequestDTO.getSearch();

        this.noticeList = Optional.ofNullable(noticeList);
        this.faqList = Optional.ofNullable(faqList);
        this.qnaList = Optional.ofNullable(qnaList);
        //🎈상품검색
        this.dtoList = Optional.ofNullable(dtoList);

        this.end = (int) (Math.ceil((double) this.pg / 10.0)) * 10;
        this.end = Math.min(this.end, this.totalPage);
        this.start = this.end - 9;
        this.start = Math.max(this.start, 1);
        int last = (int)(Math.ceil(total / (double)size));


        this.end = end == 0 ? 1 : end;
        this.prev = this.start > 1;
        this.next = this.pg < this.totalPage;

    }
}