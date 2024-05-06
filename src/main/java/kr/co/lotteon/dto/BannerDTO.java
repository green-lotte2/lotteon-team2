package kr.co.lotteon.dto;


import kr.co.lotteon.entity.Banner;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BannerDTO {

    private int bno;
    private String bname;
    private String bfile;
   // private MultipartFile file;
    private String bcolor;
    private String blink;
    private String blocation;
    private LocalDate bstartDate;
    private LocalDate bendDate;
    private LocalTime bstartTime;
    private LocalTime bendTime;
    private int bmanage;
    
    // 추가 필드
    public List<BannerDTO> bannerDTO;

    private MultipartFile imageFile;
    private String oName;
    private String sName;

    public Banner toEntity(){
        return  Banner.builder()
                    .bno(bno)
                    .bname(bname)
                    .bfile(bfile)
                    .bcolor(bcolor)
                    .blink(blink)
                    .blocation(blocation)
                    .bstartDate(bstartDate)
                    .bendDate(bendDate)
                    .bstartTime(bstartTime)
                    .bendTime(bendTime)
                    .bmanage(bmanage)
                    .build();
    }



}
