package com.libraryManagementSystem.Dto;


import lombok.Data;

@Data
public class BookResponseDto {
    private String bookTitle;
    private String description;
    private Integer stock;
    private String authorName;
    private long bookId;
}
