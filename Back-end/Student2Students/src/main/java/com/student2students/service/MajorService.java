package com.student2students.service;

import com.student2students.dto.MajorDTO;
import com.student2students.message_broker.MessagePublisher;
import com.student2students.model.Major;
import com.student2students.repository.MajorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MajorService {
    private final MajorRepository majorRepository;
    private final MessagePublisher messagePublisher;
    private Logger logger = LoggerFactory.getLogger(MajorService.class);

    @Autowired
    public MajorService(MajorRepository majorRepository,
                        MessagePublisher messagePublisher) {
        this.majorRepository = majorRepository;
        this.messagePublisher = messagePublisher;
    }

    @Transactional
    public ResponseEntity addNewMajor(MajorDTO majorDTO) {
        Major major = Major.builder()
                .majorName(majorDTO.getMajorName())
                .build();
        if(!majorRepository.existsByMajorName(major.getMajorName())){
            try {
                messagePublisher.sendMajorToPostingService(majorDTO);
                majorRepository.save(major);
                return ResponseEntity.status(201).build();
            } catch (Exception e) {
                logger.error("An error while persisting major!");
                e.printStackTrace();
                return ResponseEntity.status(500).body("Couldn't persist major");
            }
        }
        return ResponseEntity.status(403).body("Major " + major.getMajorName() + " already exists");
    }

    @Transactional
    public ResponseEntity deleteMajor(String majorName) {
        if(!majorRepository.existsByMajorName(majorName)) {
            return ResponseEntity.status(403).body("Major " + majorName + " doesn't exist!");
        }

        try {
            MajorDTO majorDTO = MajorDTO.builder()
                    .majorName(majorName)
                    .build();
            messagePublisher.sendMajorDeletedToPostingService(majorDTO);
            majorRepository.deleteByMajorName(majorName);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            logger.error("Couldn't delete major");
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<?> fetchMajors(int page, int limit) {
        if(page == 0 && limit == 0) {
            List<Major> majors = majorRepository.findAllByOrderByMajorNameAsc();
            return ResponseEntity.ok(makeDTOsFromMajor(majors));
        }

        Pageable pageableElement = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "majorName"));
        List<Major> majors = majorRepository.findAll(pageableElement).getContent();

        return ResponseEntity.ok(makeDTOsFromMajor(majors));
    }

    private List<MajorDTO> makeDTOsFromMajor(List<Major> majors) {
        return majors.stream()
                .map(major -> MajorDTO.builder()
                        .id(major.getId())
                        .majorName(major.getMajorName())
                        .build())
                .collect(Collectors.toList());
    }
}
