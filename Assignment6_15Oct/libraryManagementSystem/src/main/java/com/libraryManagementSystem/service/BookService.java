package com.libraryManagementSystem.service;


import com.libraryManagementSystem.Dto.BookRequestDto;
import com.libraryManagementSystem.Dto.BookResponseDto;
import com.libraryManagementSystem.Dto.BorrowedResponseDto;
import com.libraryManagementSystem.Exception.InsufficientStockException;
import com.libraryManagementSystem.Exception.ResourceNotFoundException;
import com.libraryManagementSystem.entity.Author;
import com.libraryManagementSystem.entity.Books;
import com.libraryManagementSystem.entity.Member;
import com.libraryManagementSystem.repository.AuthorRepo;
import com.libraryManagementSystem.repository.BookRepository;
import com.libraryManagementSystem.repository.MemberRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    MemberRepo memberRepo;

    @Autowired
    AuthorRepo authorRepo;

    public void addBook(BookRequestDto bookRequestDto){
        Books book = new Books();
        Author author = new Author();
        book.setBookTitle(bookRequestDto.getBookTitle());
        book.setDescription(bookRequestDto.getDescription());
        book.setAuthor(authorRepo.findById(bookRequestDto.getAuthorId()).get());
        book.setBookAuthor(authorRepo.getById( bookRequestDto.getAuthorId()).getName());
        book.setStock(bookRequestDto.getStock());
        book.setTotalCopies(bookRequestDto.getTotalCopies());

        bookRepository.save(book);
    }

    public void deleteBook(long id){
        bookRepository.deleteById(id);
    }

    public Books getBookById(long id){
        return bookRepository.findById(id).get();
    }

    public void updateBook (long id, BookRequestDto book) {
        Books bookdata =  getBookById(id);
        bookdata.setBookTitle(book.getBookTitle());
        bookdata.setBookAuthor(authorRepo.findById(book.getAuthorId()).get().getName());
        bookdata.setDescription(book.getDescription());
        bookdata.setStock(book.getStock());

        bookRepository.save(bookdata);
    }


    @Transactional
    public String borrowBook(Long memberId, Long bookId) {

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + memberId));
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + bookId));
        if (book.getStock() < 1) {
            throw new InsufficientStockException(
                    "Book '" + book.getBookTitle() + "' is out of stock. Available: " + book.getStock());
        }


        book.setStock(book.getStock() - 1);


        member.borrowBook(book);


        bookRepository.save(book);
        memberRepo.save(member);

        return "Book '" + book.getBookTitle() + "' borrowed successfully by " + member.getFullName();
    }


    @Transactional
    public String returnBook(Long memberId, Long bookId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + memberId));

        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + bookId));

        member.returnBook(book);


        book.setStock(book.getStock() + 1);

        bookRepository.save(book);
        memberRepo.save(member);

        return "Book '" + book.getBookTitle() + "' returned successfully";
    }


    public List<BorrowedResponseDto> getAllBorrowedBooks() {
        List<Books> borrowedBooks = bookRepository.findAllBorrowedBooks();
        List<BorrowedResponseDto> result = new ArrayList<>();
        for (Books book : borrowedBooks) {
            for (Member member : book.getMembers()) {
                BorrowedResponseDto dto = new BorrowedResponseDto();
                dto.setBookId(book.getBookId());
                dto.setBookTitle(book.getBookTitle());
                dto.setAuthorName(book.getBookAuthor());
                dto.setMemberId(member.getMemberId());
                dto.setMemberName(member.getFullName());
                dto.setContactInfo(member.getContactInfo());

                result.add(dto);
            }
        }

        return result;
    }


    public List<BookResponseDto> getAvailableBooks() {

        return bookRepository.findAvailableBooks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookResponseDto convertToDTO(Books book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setBookTitle(book.getBookTitle());
        dto.setStock(book.getStock());
        dto.setAuthorName(book.getBookAuthor());
        dto.setDescription(book.getDescription());
        dto.setBookId(book.getBookId());
        return dto;
    }

}
