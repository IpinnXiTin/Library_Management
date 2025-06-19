package com.facenet.library_management.dto.response;

import com.facenet.library_management.entity.LoanRecord;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderResponse {

    String readerId;

    String fullName;

    LocalDate dateOfBirth;

    String phoneNumber;

    String email;
    Set<LoanRecord> loanRecords;
}
