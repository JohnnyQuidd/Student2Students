package com.student2students.postservice.service;

import com.student2students.postservice.dto.ExchangeDTO;
import com.student2students.postservice.model.Exchange;
import com.student2students.postservice.repository.ExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeService {
    private final ExchangeRepository exchangeRepository;
    private final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    @Autowired
    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }


    @Transactional
    public ResponseEntity<?> createNewExchange(ExchangeDTO exchangeDTO, String username) {
        Exchange exchange = createExchangeFromDTO(exchangeDTO, username);
        try {
            exchangeRepository.save(exchange);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Couldn't persist exchange");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private Exchange createExchangeFromDTO(ExchangeDTO exchangeDTO, String username) {
//      TODO: Check if dateTime conversion is necessary for JavaScript exchangeStart&End times
        return Exchange.builder()
                .studentUsername(username)
                .country(exchangeDTO.getCountry())
                .city(exchangeDTO.getCity())
                .university(exchangeDTO.getUniversity())
                .minNumberOfAttendees(exchangeDTO.getMinNumberOfAttendees())
                .maxNumberOfAttendees(exchangeDTO.getMaxNumberOfAttendees())
                .exchangeStart(exchangeDTO.getExchangeStart())
                .exchangeEnding(exchangeDTO.getExchangeEnding())
                .createdAt(LocalDateTime.now())
                .price(exchangeDTO.getPrice())
                .body(exchangeDTO.getBody())
                .build();
    }

    public ResponseEntity<?> fetchExchanges(int page, int limit) {
        if(page == 0 && limit == 0) {
            List<Exchange> exchanges = exchangeRepository.findAll();
            return ResponseEntity.ok(exchanges);
        }
        Pageable pageElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "exchangeStart"));
        List<Exchange> exchanges = exchangeRepository.findAll(pageElement).getContent();

        return ResponseEntity.ok(exchanges);
    }
}
