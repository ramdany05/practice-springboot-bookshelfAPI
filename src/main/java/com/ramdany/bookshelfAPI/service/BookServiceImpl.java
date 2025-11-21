package com.ramdany.bookshelfAPI.service;

import com.ramdany.bookshelfAPI.dto.BookRequest;
import com.ramdany.bookshelfAPI.service.BookService;
import com.ramdany.bookshelfAPI.repository.BookRepository;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public String addBook(BookRequest request){
//        validasi name
        if(request.getName() != null || request.getName().isBlank()){
            throw new BadRequestException("Failed Add Book. Please fill the book field");
        }

//        validasi readPag !> pageCount
        if(request.getReadPage() > request.getPageCount()){
            throw new BadRequestException("Failed Add Book. Readpage cannot bigger than pagecount");
        }
    }
}
