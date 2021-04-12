package com.student2students.dto;

import com.student2students.model.Major;
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
