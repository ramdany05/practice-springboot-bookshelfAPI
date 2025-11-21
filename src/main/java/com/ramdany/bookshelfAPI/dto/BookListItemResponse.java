package com.ramdany.bookshelfAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookListItemResponse {
    private String id;
    private String name;
    private String publisher;
}
