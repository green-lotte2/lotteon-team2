package kr.co.lotteon.service;

import kr.co.lotteon.dto.MyHomeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MyService {

    private final ModelMapper modelMapper;
    private final QnaRepository qnaRepository;

    public MyHomeDTO getMyHomeInfo(String uid){
        List<QnaDTO> qnaList = qnaRepository.findTop3ByUidOrderByRdate(uid).stream().map(entity -> modelMapper.map(entity, QnaDTO.class)).toList();

        return MyHomeDTO.builder()
                .qnaList(qnaList)
                .build();
    }
}
