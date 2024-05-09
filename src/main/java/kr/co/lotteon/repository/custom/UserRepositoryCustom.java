package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserRepositoryCustom {
    public Page<Tuple> adminSelectUsers(PageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> adminSearchUsers(PageRequestDTO pageRequestDTO, Pageable pageable);

}
