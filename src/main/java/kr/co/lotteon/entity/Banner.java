package kr.co.lotteon.entity;


import jakarta.persistence.*;
import kr.co.lotteon.dto.BannerDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String bname;
    private String bfile;
    private String bcolor;
    private String blink;
    private String bloaction;
    @CreationTimestamp
    private LocalDateTime bstartDate;
    @CreationTimestamp
    private LocalDateTime bendDate;
    @CreationTimestamp
    private LocalDateTime bstartTime;
    @CreationTimestamp
    private LocalDateTime endTime;
    private String bmanage;

    //private List<BannerDTO> bannerList;
}












