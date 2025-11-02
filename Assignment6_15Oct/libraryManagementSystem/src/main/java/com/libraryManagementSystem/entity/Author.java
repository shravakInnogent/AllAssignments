package com.libraryManagementSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Author")
public class Author {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    long authorId;

    String name;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Books> books = new ArrayList<>();

}
