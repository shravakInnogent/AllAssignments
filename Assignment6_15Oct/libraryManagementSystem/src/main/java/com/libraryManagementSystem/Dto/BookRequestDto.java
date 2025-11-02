package com.libraryManagementSystem.Dto;


import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class BookRequestDto {
    private String bookTitle;
    private String description;
    private long authorId;
    private Integer stock;
    private Integer totalCopies;
}
