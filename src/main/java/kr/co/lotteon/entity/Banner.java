package kr.co.lotteon.entity;


import jakarta.persistence.*;
import kr.co.lotteon.dto.BannerDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


    private LocalDate bstartDate;
    private LocalDate bendDate;
    private LocalTime bstartTime;
    private LocalTime bendTime;
    private int bmanage;

}












