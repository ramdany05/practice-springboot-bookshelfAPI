package com.ramdany.bookshelfAPI.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(length = 64)
    private String id;

    @Column(nullable = false)
    private String name;

    private int year;

    private String author;

    @Column(columnDefinition = "Text")
    private String summary;

    private String publisher;

    private int pageCount;

    private int readPage;

    private boolean finished;

    private boolean reading;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String updateAt;
}
