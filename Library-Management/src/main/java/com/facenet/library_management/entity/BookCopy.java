package com.facenet.library_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookCopy extends BaseEntity {

    @Id
    String bookCopyId;

    String location;

    String status;

    @ManyToOne
    @JoinColumn(name = "book_title_id")
    BookTitle bookTitle;

    @OneToMany(mappedBy = "bookCopy")
    Set<LoanRecord> loanRecords;
}
