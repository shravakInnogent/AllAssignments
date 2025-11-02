package com.libraryManagementSystem.service;

import com.libraryManagementSystem.Exception.AuthorNotFoundException;
import com.libraryManagementSystem.entity.Author;
import com.libraryManagementSystem.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;

    public void add(Author author)
    {
        authorRepo.save(author);
    }

    public Author getAuthor(Long id) {
        return authorRepo.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException());
    }

    public List<Author> getAllAuthor() {
        return authorRepo.findAll();
    }

    public void deleteAuthor(long id){
        authorRepo.deleteById(id);
    }
}
