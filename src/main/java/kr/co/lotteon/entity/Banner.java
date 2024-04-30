package kr.co.lotteon.entity;


import jakarta.persistence.*;
import kr.co.lotteon.dto.BannerDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String blocation;


    private LocalDateTime bstartDate;
    private LocalDateTime bendDate;
    private LocalDateTime bstartTime;
    private LocalDateTime bendTime;
    private String bmanage;

}












