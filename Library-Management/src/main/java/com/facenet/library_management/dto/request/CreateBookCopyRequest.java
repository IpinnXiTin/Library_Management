package com.facenet.library_management.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBookCopyRequest {

    String bookCopyId;
    String location;
    String status;
    String bookTitleId;

}
