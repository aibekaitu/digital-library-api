package com.example.digitallibraryapi.repo;

import com.example.digitallibraryapi.dto.BookDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class BookJdbcRepository {

    private final JdbcTemplate jdbc;

    public BookJdbcRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int create(BookDto b) {
        String sql = """
            insert into books(title, author, isbn, published_year)
            values (?, ?, ?, ?)
            """;
        return jdbc.update(sql, b.title, b.author, b.isbn, b.publishedYear);
    }

    public int updateByIsbn(String isbn, BookDto b) {
        String sql = """
            update books
            set title = ?, author = ?, published_year = ?
            where isbn = ?
            """;
        return jdbc.update(sql, b.title, b.author, b.publishedYear, isbn);
    }
    public List<BookDto> findAll() {
        String sql = """
            select id, title, author, isbn, published_year
            from books
            order by id
            """;

        return jdbc.query(sql, (rs, rowNum) -> {
            BookDto b = new BookDto();
            b.id = rs.getLong("id");
            b.title = rs.getString("title");
            b.author = rs.getString("author");
            b.isbn = rs.getString("isbn");
            b.publishedYear = rs.getInt("published_year");
            return b;
        });
    }
}
