package com.facenet.library_management.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBookTitleRequest {

    String bookTitleId;
    String title;
    List<String> author;
    String category;
    String publicationYear;
    String publisher;
    String pageCount;
}

