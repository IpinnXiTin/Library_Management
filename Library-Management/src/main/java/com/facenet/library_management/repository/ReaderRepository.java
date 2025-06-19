package com.facenet.library_management.repository;

import com.facenet.library_management.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    @Query("SELECT r FROM Reader r WHERE LOWER(r.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Reader> findByReaderName(String name);
}
