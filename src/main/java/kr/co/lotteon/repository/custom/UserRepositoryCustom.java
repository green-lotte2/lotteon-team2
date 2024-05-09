package kr.co.lotteon.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    public Page<Tuple> adminSelectUsers(PageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Tuple> adminSearchUsers(PageRequestDTO pageRequestDTO, Pageable pageable);

    //public void adminSelectUsers(String uid, PageRequestDTO pageRequestDTO);
}
