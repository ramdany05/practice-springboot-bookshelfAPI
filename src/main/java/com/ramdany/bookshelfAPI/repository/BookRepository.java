package com.ramdany.bookshelfAPI.repository;

import com.ramdany.bookshelfAPI.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByNameContainingIgnoringCase(String name);

    List<Book> findByReading(boolean reading);

    List<Book> findByFinished(boolean finished);
}
