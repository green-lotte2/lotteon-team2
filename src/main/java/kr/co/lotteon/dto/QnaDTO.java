package kr.co.lotteon.dto;

import kr.co.lotteon.entity.CsQna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaDTO {

    private int qnano;
    private int cate1;
    private int cate2;
    private String title;
    private String content;
    private String file1;
    private String file2;
    private String file3;
    private String file4;
    private String uid;
    private String ono;     //null 값을 넣으려면 String을 써야함
    private String prodno;
    private int parent;
    private int answercomplete;
    private String regip;
    private LocalDateTime rdate;

    private MultipartFile mFile1;


    //추가 필드
    private String writername;
    private String c1name;
    private String c2name;
    private String writermarking;
    private String rdatesub;


    private QnaDTO comment;

    public String getRdateSub() {
        String formatDate = rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        return formatDate;
    }

    public CsQna toEntity(){
        return CsQna.builder()
                .qnano(qnano)
                .cate1(cate1)
                .cate2(cate2)
                .title(title)
                .content(content)
                .file1(file1)
                .file2(file2)
                .uid(uid)
                .ono(ono)
                .prodno(prodno)
                .parent(parent)
                .answercomplete(answercomplete)
                .regip(regip)
                .rdate(rdate)
                .build();
    }
}
