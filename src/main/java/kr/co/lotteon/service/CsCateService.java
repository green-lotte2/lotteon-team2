package kr.co.lotteon.service;

import kr.co.lotteon.dto.Cate1DTO;
import kr.co.lotteon.dto.Cate2DTO;
import kr.co.lotteon.dto.FaqDTO;
import kr.co.lotteon.entity.CsCate1;
import kr.co.lotteon.entity.CsCate2;
import kr.co.lotteon.repository.Cate1Repository;
import kr.co.lotteon.repository.Cate2Repository;
import kr.co.lotteon.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsCateService {

    private final Cate1Repository cate1Repository;
    private final Cate2Repository cate2Repository;
    private FaqRepository faqRepository;


    public List<Cate1DTO> selectCate1s() {
        return cate1Repository.findAll().stream().map(CsCate1::toDTO).collect(Collectors.toList());
    }

    public List<Cate2DTO> selectCate2s(int cate1) {
        return cate2Repository.findAllByCate1(cate1).stream().map(CsCate2::toDTO).collect(Collectors.toList());
    }

    public Map<String, Object> getCsCates() {
        Map<String, Object> map = new HashMap<>();

        List<Cate1DTO> cate1List = selectCate1s();

        List<Map> depth1 = new ArrayList<>();
        Map<Integer, List> depth2 = new HashMap<>();

        for (Cate1DTO cate1 : cate1List) {
            // 1차 카테고리 List에 MapObject로 저장함
            Map<String, String> depth1_temp = new HashMap<>();
            depth1_temp.put("cate1", cate1.getCate1() + "");
            depth1_temp.put("c1name", cate1.getC1name());
            depth1.add(depth1_temp);

            // 2차 카테고리 data
            List<Cate2DTO> cate2item = selectCate2s(cate1.getCate1());

            // 2차 카테고리 Map에 MapObject로 저장함
            List<Map> depth2_2 = new ArrayList<>();
            Map<String, String> depth2_temp = null;
            for (Cate2DTO dto2 : cate2item) {
                depth2_temp = new HashMap<>();
                depth2_temp.put("cate1", dto2.getCate1() + "");
                depth2_temp.put("cate2", dto2.getCate2() + "");
                depth2_temp.put("c2name", dto2.getC2name());
                depth2_2.add(depth2_temp);
            }
            depth2.put(cate1.getCate1(), depth2_2);
        }
        map.put("depth1", depth1);
        map.put("depth2", depth2);

        return map;
    }

}