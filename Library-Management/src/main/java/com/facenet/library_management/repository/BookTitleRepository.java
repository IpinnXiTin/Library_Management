package com.facenet.library_management.repository;

import com.facenet.library_management.entity.BookTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTitleRepository extends JpaRepository<BookTitle, String> {

    boolean existsByBookTitleId(String bookTitleId);

    @Query("SELECT CASE WHEN COUNT(bc) > 0 THEN true ELSE false END FROM BookCopy bc WHERE bc.bookTitle.id = :bookTitleId")
    boolean hasBookCopies(@Param("bookTitleId") String bookTitleId);

    List<BookTitle> findByTitleContainingIgnoreCase(String title);

    List<BookTitle> findByCategory(String category);

    @Query("SELECT bt FROM BookTitle bt WHERE :author MEMBER OF bt.author")
    List<BookTitle> findByAuthor(@Param("author") String author);
}
