package com.libraryManagementSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Member {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberId;

    @Column
    String fullName;

    @Column
    String contactInfo;


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "borrowed_books",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private Set<Books> borrowedBooks = new HashSet<>();

    public void borrowBook(Books book) {
        borrowedBooks.add(book);
        book.getMembers().add(this);
    }

    public void returnBook(Books book) {
        borrowedBooks.remove(book);
        book.getMembers().remove(this);
    }

}
