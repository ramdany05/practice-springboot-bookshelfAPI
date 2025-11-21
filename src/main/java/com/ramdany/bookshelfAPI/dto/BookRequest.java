package com.ramdany.bookshelfAPI.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank (message = "Failed to add new book, Please fill the field of name !")
    private String name;

    @NotNull(message = "Year is required")
    private Integer year;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Summary is required")
    private String summary;

    @NotBlank(message = "Publisher is required")
    private String publisher;

    @NotNull(message = "pageCount is required")
    @Min(value = 0, message = "Value must be a positive")
    private Integer pageCount;

    @NotNull(message = "readPage is required")
    @Min(value = 0, message = "Value must be a positive")
    private Integer readPage;

    @NotNull(message = "reading is required")
    private Boolean reading;

}
