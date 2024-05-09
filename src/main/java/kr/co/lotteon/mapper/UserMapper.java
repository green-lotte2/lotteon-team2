package kr.co.lotteon.mapper;

import kr.co.lotteon.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserMapper {

    public UserDTO selectUser(String uid);
    public UserDTO selectSeller(String uid);

}
