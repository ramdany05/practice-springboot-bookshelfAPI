package com.ramdany.bookshelfAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailResponse {
    private String id;
    private String name;
    private int year;
    private String author;
    private String summary;
    private String publisher;
    private int pageCount;
    private int readPage;
    private boolean finished;
    private boolean reading;
    private String createdAt;
    private String updateAt;
}
