package kr.co.lotteon.dto;


import kr.co.lotteon.entity.Banner;
import lombok.*;

import java.util.List;

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
    private String bstartDate;
    private String bendDate;
    private String bstartTime;
    private String bendTime;
    private String bmanage;

    private List<BannerDTO> bannerDTOS;

    public Banner toEntity(){
        return  Banner.builder()
                    .bno(bno)
                    .bname(bname)
                    .bfile(bfile)
                    .bcolor(bcolor)
                    .blink(blink)
                    .bloaction(blocation)
                    .bmanage(bmanage)
                    .build();
    }

    public List<BannerDTO> bannerDTO;

}
