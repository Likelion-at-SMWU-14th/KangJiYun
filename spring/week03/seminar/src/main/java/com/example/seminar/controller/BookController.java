package com.example.seminar.controller;

import com.example.seminar.dto.Book;
import com.example.seminar.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;


    @GetMapping
    public List<Book> books(){
        return bookService.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }
}
