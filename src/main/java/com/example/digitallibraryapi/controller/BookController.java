package com.example.digitallibraryapi.controller;

import com.example.digitallibraryapi.dto.BookDto;
import com.example.digitallibraryapi.repo.BookJdbcRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookJdbcRepository repo;

    public BookController(BookJdbcRepository repo) {
        this.repo = repo;
    }

    // GET /api/books
    @GetMapping
    public List<BookDto> getAll() {
        return repo.findAll();
    }

    // POST /api/books  (JSON body)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDto b) {
        if (b.title == null || b.title.isBlank()) return ResponseEntity.badRequest().body("title is required");
        if (b.author == null || b.author.isBlank()) return ResponseEntity.badRequest().body("author is required");
        if (b.isbn == null || b.isbn.isBlank()) return ResponseEntity.badRequest().body("isbn is required");
        if (b.publishedYear == null) return ResponseEntity.badRequest().body("publishedYear is required");

        int rows = repo.create(b);
        if (rows == 0) return ResponseEntity.badRequest().body("book with this isbn already exists");
        return ResponseEntity.ok(b);
    }
}
