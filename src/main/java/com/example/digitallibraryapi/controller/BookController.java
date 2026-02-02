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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDto b) {
        int rows = repo.create(b);
        if (rows == 0) {
            return ResponseEntity.badRequest().body("Book with this ISBN already exists");
        }
        return ResponseEntity.ok(b);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<?> update(
            @PathVariable String isbn,
            @RequestBody BookDto b
    ) {
        if (b.title == null || b.title.isBlank())
            return ResponseEntity.badRequest().body("Title required");

        if (b.author == null || b.author.isBlank())
            return ResponseEntity.badRequest().body("Author required");

        if (b.publishedYear == null)
            return ResponseEntity.badRequest().body("Published year required");

        int rows = repo.updateByIsbn(isbn, b);
        if (rows == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(b);
    }
    @GetMapping
    public List<BookDto> getAll() {
        return repo.findAll();
    }

}
