package com.libraryManagementSystem.Dto;
import lombok.Data;

@Data
public class BorrowRequestDto {
    private Long memberId;
    private Long bookId;
}
