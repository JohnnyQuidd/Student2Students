package com.student2students.postservice.dto;

import com.student2students.postservice.model.Major;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorListDTO {
    private List<Major> majors;
}
