package kr.co.lotteon.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductimgDTO {
    private int pno;
    private String mainimg;
    private String subimg;
    private String detailimg;

    private List<MultipartFile> files;

}
