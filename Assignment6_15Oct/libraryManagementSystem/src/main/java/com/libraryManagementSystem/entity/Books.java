package com.libraryManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.IdGeneratorType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookId;

    @Column
    String bookTitle;

    @Column
    String bookAuthor;

    @Column
    String description;

    @Column(nullable = false)
    private Integer stock ;

    @Column(nullable = false)
    Integer totalCopies;


    @ManyToMany(mappedBy = "borrowedBooks")
    @ToString.Exclude           // Prevents infinite loop in toString()
    @EqualsAndHashCode.Exclude  // Prevents infinite loop in equals/hashCode
    @JsonIgnore                 // Prevents infinite loop in JSON serialization
    private Set<Member> members = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;

}
