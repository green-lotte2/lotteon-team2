package kr.co.lotteon.dto;


import kr.co.lotteon.entity.Banner;
import lombok.*;

import java.time.LocalDateTime;
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
    private String bcolor;
    private String blink;
    private String blocation;
    private LocalDateTime bstartDate;
    private LocalDateTime bendDate;
    private LocalDateTime bstartTime;
    private LocalDateTime bendTime;
    private String bmanage;
    
    public List<BannerDTO> bannerDTO;

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
