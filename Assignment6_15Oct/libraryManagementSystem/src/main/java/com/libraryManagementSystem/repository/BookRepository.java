package com.libraryManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.libraryManagementSystem.entity.Books;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long>
{

    @Query("SELECT b FROM Books b JOIN b.members m")
    List<Books> findAllBorrowedBooks();

    @Query("SELECT b FROM Books b WHERE b.stock > 0")
    List<Books> findAvailableBooks();
}
