package com.facenet.library_management.repository;

import com.facenet.library_management.entity.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRecordRepository extends JpaRepository<LoanRecord, String> {

    @Query("select lr from LoanRecord lr where lr.bookCopy.bookCopyId = :bookCopyId")
    List<LoanRecord> findByBookCopyId(@Param("bookCopyId") String bookCopyId);

    @Query("select lr from LoanRecord lr where lr.reader.readerId = :readerId")
    List<LoanRecord> findByReaderId(@Param("readerId") String readerId);
}   
