package com.facenet.library_management.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookTitle extends BaseEntity {

    @Id
    String bookTitleId;

    String title;

    @ElementCollection
    List<String> author;
    String category;
    String publicationYear;
    String publisher;
    String pageCount;

    @OneToMany(mappedBy = "bookTitle")
    Set<BookCopy> bookCopies;

}
