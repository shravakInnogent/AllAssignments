package com.libraryManagementSystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedResponseDto {
    private Long bookId;
    private String bookTitle;
    private String authorName;
    private Long memberId;
    private String memberName;
    private String contactInfo;
}
