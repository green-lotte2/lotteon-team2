package kr.co.lotteon.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import kr.co.lotteon.dto.UserDTO;


@Mapper
public interface AdminMapper{

    public List<UserDTO> adminSelectUsers();

}
