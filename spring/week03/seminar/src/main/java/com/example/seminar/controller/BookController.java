package com.example.seminar.controller;

import com.example.seminar.dto.Book;
import com.example.seminar.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String books(Model page){
        var books = bookService.findAll();
        page.addAttribute("books",books);
        return "books";
    }

    @PostMapping("/books")
    public String addBook(Book book, Model page){
        bookService.addBook(book);
        var books = bookService.findAll();
        page.addAttribute("books",books);
        return "books";
    }
}
