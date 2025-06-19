package com.facenet.library_management.dto.response;

import com.facenet.library_management.entity.BookCopy;
import com.facenet.library_management.entity.Reader;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanRecordResponse {

    String loanRecordId;
    Reader reader;
    BookCopy bookCopy;

    LocalDateTime expectedReturnDate;

    LocalDateTime actualReturnDate;

    String note;
}
