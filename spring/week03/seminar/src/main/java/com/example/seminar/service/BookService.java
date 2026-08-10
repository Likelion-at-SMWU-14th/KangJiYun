package com.example.seminar.service;

import com.example.seminar.dto.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private List<Book> books = new ArrayList<>();

    public Book addBook(Book book){
        books.add(book);
        return book;
    }

    public List<Book> findAll(){
        return books;
    }
}
