package com.facenet.library_management.repository;

import com.facenet.library_management.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {

    boolean existsByBookCopyId(String bookCopyId);

    @Query("SELECT bc FROM BookCopy bc WHERE bc.bookTitle.bookTitleId = :bookTitleId")
    List<BookCopy> findByBookTitleId(@Param("bookTitleId") String bookTitleId);

    List<BookCopy> findByStatus(String status);

    List<BookCopy> findByLocation(String location);

    @Query("SELECT CASE WHEN COUNT(lr) > 0 THEN true ELSE false END FROM LoanRecord lr WHERE lr.bookCopy.bookCopyId = :bookCopyId AND lr.actualReturnDate IS NULL")
    boolean isBookCopyBorrowed(@Param("bookCopyId") String bookCopyId);

}
