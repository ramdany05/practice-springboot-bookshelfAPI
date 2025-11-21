package com.ramdany.bookshelfAPI.service;

import com.ramdany.bookshelfAPI.dto.BookDetailResponse;
import com.ramdany.bookshelfAPI.dto.BookListItemResponse;
import com.ramdany.bookshelfAPI.dto.BookRequest;

import java.util.List;

public interface BookService {
//    add book
    String addBook(BookRequest request);

//    get all book
    List<BookListItemResponse> getAllBooks();

//    get book by id
    BookDetailResponse getBookById(String id);

//    update book
    void updateBook(String id, BookRequest request);

//    remove book
    void deleteBook(String id);




}
