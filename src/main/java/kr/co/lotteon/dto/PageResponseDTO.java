package kr.co.lotteon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class PageResponseDTO {
    private List<NoticeDTO> noticeList;
    private List<FaqDTO> faqList;
    private List<QnaDTO> qnaList;
    private List<OrdersDTO> orderList;
    // 상품검색 결과
    private List<ProductDTO> dtoList;

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
                           List<FaqDTO> faqList, List<QnaDTO> qnaList, List<ProductDTO> dtoList, List<OrdersDTO> orderList) {

        this.cate1 = pageRequestDTO.getCate1();
        this.cate2 = pageRequestDTO.getCate2();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.totalPage = (int) Math.ceil((double) total / this.size);
        this.search = pageRequestDTO.getSearch();

        this.noticeList = noticeList;
        this.faqList = faqList;
        this.qnaList = qnaList;
        this.dtoList = dtoList;
        this.orderList = orderList;

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