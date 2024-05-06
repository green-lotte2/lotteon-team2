package kr.co.lotteon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TypePageResponseDTO {


    private int pg;
    private int size;
    private int total;
    private int totalPage;

    private String type;
    private String keyword;

    private List<?> anyList;

    private int start, end;
    private boolean prev, next;

    @Builder
    public TypePageResponseDTO(PageRequestDTO pageRequestDTO, int total, List<?> anyList) {

        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.totalPage = (int) Math.ceil((double) total / this.size);

        this.type = pageRequestDTO.getType();
        this.keyword = pageRequestDTO.getKeyword();

        this.anyList = anyList;

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