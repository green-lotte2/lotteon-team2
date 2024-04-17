package kr.co.lotteon.service;

import groovy.util.logging.Slf4j;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.entity.Terms;
import kr.co.lotteon.repository.TermsRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TermsService {

    private final TermsRepository repository;
    private final ModelMapper modelMapper;

    public TermsDTO selectTerms() {
        Optional<Terms> result = repository.findById(1);

        if (result.isPresent()) {
            Terms terms = result.get();
            return modelMapper.map(terms, TermsDTO.class);
        } else {
            return null;
        }
    }
}

