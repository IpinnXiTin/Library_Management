package com.facenet.library_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoanRecord extends BaseEntity {

    @Id
    String loanRecordId;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    BookCopy bookCopy;

    LocalDateTime expectedReturnDate;

    LocalDateTime actualReturnDate;

    String note;
}
