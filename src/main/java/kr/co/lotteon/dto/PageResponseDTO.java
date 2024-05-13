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

    //🎈admin user 검색
    private List<UserDTO> userList;
    private int startNo;
    private String role;
    private String type;
    private String keyword;

    //🎈배송관리 리스트
    private List<OrderDetailDTO> deliveryList;





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
                           List<FaqDTO> faqList, List<QnaDTO> qnaList, List<ProductDTO> dtoList, List<OrdersDTO> orderList, List<UserDTO> userList, List<OrderDetailDTO> deliveryList) {

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
        //🎈 user 리스트
        this.userList = userList;

        //🎈 delivery 리스트
        this.deliveryList = deliveryList;


        this.startNo = total - ((pg - 1) * size);
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