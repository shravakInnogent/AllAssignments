package com.innogent.demo.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class StudentRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private Set<Integer> courseIds;
}
