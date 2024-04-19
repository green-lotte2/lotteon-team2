package kr.co.lotteon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CsPageResponseDTO {
    private List<NoticeDTO> noticeList;
    private List<FaqDTO> faqList;
    private List<QnaDTO> qnaList;

    private int pg;
    private int size;
    private int total;
    private int cate1;
    private int cate2;
    private String search;

    private int start, end;
    private boolean prev, next;

    @Builder
    public CsPageResponseDTO(CsPageRequestDTO pageRequestDTO,
                             List<NoticeDTO> noticeList, int total,
                             List<FaqDTO> faqList, List<QnaDTO> qnaList) {
        this.cate1 = pageRequestDTO.getCate1();
        this.cate2 = pageRequestDTO.getCate2();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.search = pageRequestDTO.getSearch();

        this.noticeList = noticeList;
        this.faqList = faqList;
        this.qnaList = qnaList;

        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int last = (int)(Math.ceil(total / (double)size));

        this.end = end > last ? last:end;
        this.end = end == 0 ? 1 : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}