package com.facenet.library_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookTitleResponse {
    String bookTitleId;

    String title;
    List<String> author;
    String category;
    String publicationYear;
    String publisher;
    String pageCount;

    Set<BookCopyResponse> bookCopies;
}
