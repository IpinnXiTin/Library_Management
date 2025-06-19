package com.facenet.library_management.dto.response;

import com.facenet.library_management.entity.LoanRecord;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyResponse {

    String bookCopyId;
    String location;
    String status;
    String bookTitleId;
    Set<LoanRecord> loanRecords;
}
