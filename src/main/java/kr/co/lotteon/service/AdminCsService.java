package kr.co.lotteon.service;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.CsFaq;
import kr.co.lotteon.entity.CsNotice;
import kr.co.lotteon.entity.CsQna;
import kr.co.lotteon.mapper.AdminMapper;
import kr.co.lotteon.repository.FaqRepository;
import kr.co.lotteon.repository.NoticeRepository;
import kr.co.lotteon.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCsService {

    @Autowired
    NoticeRepository noticeRepository;

    @Autowired
    FaqRepository faqRepository;

    @Autowired
    QnaRepository qnaRepository;

    @Autowired
    AdminMapper adminMapper;



    /////////////////////////
    // 공지사항 //////////////
    ////////////////////////
    public void saveNotice(NoticeDTO dto){

        // DTO -> Entity 변환
        CsNotice entity = dto.toEntity();

        // DB insert
        noticeRepository.save(entity);
    }

    //🎈 공지사항 리스트
    public List<NoticeDTO> noticeList(){

        return noticeRepository.findAll()
                .stream()
                .map(
                        CsNotice::toDTO
                )
                .collect(Collectors.toList());
    }


    // Notice 리스트
    public PageResponseDTO noticeList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("noticeno");
        Page<CsNotice> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = noticeRepository.findAll(pageable);
            }else{
                result = noticeRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        }else{
            if(pageRequestDTO.getSearch().equals("")){
                result = noticeRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            }else{
                result = noticeRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<NoticeDTO> dtoList = result.getContent()
                .stream()
                .map(
                        CsNotice::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .noticeList(dtoList)
                .total(totalElement)
                .build();
    }




    /////////////////////////
    // 1:1 질문 /////////////
    ////////////////////////
    public List<QnaDTO> qnaList(){

        return qnaRepository.findAll()
                .stream()
                .map(
                        CsQna::toDTO
                )
                .collect(Collectors.toList());
    }

    // Qna 리스트
    public PageResponseDTO qnaList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("qnano");
        Page<CsQna> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = qnaRepository.findAll(pageable);
            }else{
                result = qnaRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        }else{
            if(pageRequestDTO.getSearch().equals("")){
                result = qnaRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            }else{
                result = qnaRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<QnaDTO> dtoList = result.getContent()
                .stream()
                .map(
                        CsQna::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .qnaList(dtoList)
                .total(totalElement)
                .build();
    }

    



    // 🎈Faq 리스트
    public List<FaqDTO> faqList(){

        return faqRepository.findAll()
                .stream()
                .map(
                        CsFaq::toDTO
                )
                .collect(Collectors.toList());
    }


    // FAQ 리스트
    public PageResponseDTO faqList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("faqno");
        Page<CsFaq> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = faqRepository.findAll(pageable);
            }else{
                result = faqRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        }else{
            if(pageRequestDTO.getSearch().equals("")){
                result = faqRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            }else{
                result = faqRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<FaqDTO> dtoList = result.getContent()
                .stream()
                .map(
                        CsFaq::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .faqList(dtoList)
                .total(totalElement)
                .build();
    }


    //🎈 공지사항 뷰
    public NoticeDTO adminNoticeView(int noticeno){
        return adminMapper.adminNoticeView(noticeno);
    }
    public FaqDTO adminFaqView(int faqno){ return adminMapper.adminFaqView(faqno);}
}



























