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
    private int startNo;
    private boolean prev, next;
    private String type;
    private String keyword;

    @Builder
    public ProductPageResponseDTO(ProductPageRequestDTO productPageRequestDTO, List<ProductDTO> dtoList, int total, int pg, int size) {
        this.pg = productPageRequestDTO.getPg();
        this.size = productPageRequestDTO.getSize();
        this.total = total;
        this.cate = productPageRequestDTO.getCate();
        this.totalPage = (int) Math.ceil((double) total / this.size);
        this.search = productPageRequestDTO.getSearch();

        this.type = productPageRequestDTO.getType();
        this.keyword = productPageRequestDTO.getKeyword();

        this.dtoList = dtoList;

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg/10.0))*10;
        this.start = this.end - 9;

        int last = (int) (Math.ceil(total / (double) size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
    }