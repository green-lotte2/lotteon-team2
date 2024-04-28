package kr.co.lotteon.repository;


import kr.co.lotteon.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository  extends JpaRepository<Reply, Integer> {


    public List<Reply> findByQnano(int qnano);
    public Optional<Reply> findById(int qnano);

}
