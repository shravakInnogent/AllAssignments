package com.libraryManagementSystem.controller;


import com.libraryManagementSystem.Dto.BookRequestDto;
import com.libraryManagementSystem.Dto.BookResponseDto;
import com.libraryManagementSystem.Dto.BorrowRequestDto;
import com.libraryManagementSystem.Dto.BorrowedResponseDto;
import com.libraryManagementSystem.entity.Books;
import com.libraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public void addBook(@RequestBody BookRequestDto bookRequestDto){
        bookService.addBook(bookRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/getBookById/{id}")
    public Books getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    @PutMapping("/updateBook/{id}")
    public void updateBook(@RequestBody BookRequestDto book ,@PathVariable long id){
        bookService.updateBook(id, book);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponseDto>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    @GetMapping("/borrowedBook")
    public ResponseEntity<List<BorrowedResponseDto>> getAllBorrowedBooks() {
        List<BorrowedResponseDto> borrowedBooks = bookService.getAllBorrowedBooks();
        return ResponseEntity.ok(borrowedBooks);
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequestDto request) {
        String message = bookService.borrowBook(request.getMemberId(), request.getBookId());
        return ResponseEntity.ok(message);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody BorrowRequestDto request) {
        String message = bookService.returnBook(request.getMemberId(), request.getBookId());
        return ResponseEntity.ok(message);
    }
}
