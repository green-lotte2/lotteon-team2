package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.Cate1DTO;
import kr.co.lotteon.dto.Cate2DTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CateMapper {

    public List<Cate1DTO> selectCate1();
    public List<Cate2DTO> selectCate2(int cate1);
    public List<Cate2DTO> adminSelectCate2();

}
