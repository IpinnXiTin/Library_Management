package com.facenet.library_management.service;

import com.facenet.library_management.dto.request.CreateBookCopyRequest;
import com.facenet.library_management.dto.request.UpdateBookCopyRequest;
import com.facenet.library_management.dto.response.BookCopyResponse;
import com.facenet.library_management.entity.BookCopy;
import com.facenet.library_management.entity.BookTitle;
import com.facenet.library_management.entity.LoanRecord;
import com.facenet.library_management.enums.ErrorCode;
import com.facenet.library_management.exception.AppException;
import com.facenet.library_management.repository.BookCopyRepository;
import com.facenet.library_management.repository.BookTitleRepository;
import com.facenet.library_management.repository.LoanRecordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookCopyService {

    BookCopyRepository bookCopyRepository;
    BookTitleRepository bookTitleRepository;
    LoanRecordRepository loanRecordRepository;

    public BookCopyResponse createNewBookCopy(CreateBookCopyRequest request) {

        if (bookCopyRepository.existsByBookCopyId(request.getBookCopyId())) {
            throw new AppException(ErrorCode.BOOKCOPY_EXISTED);
        }

        BookTitle bookTitle = bookTitleRepository.findById(request.getBookTitleId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKTITLE_NOT_FOUND));

        BookCopy bookCopy = BookCopy.builder()
                .bookCopyId(request.getBookCopyId())
                .bookTitle(bookTitle)
                .status(request.getStatus())
                .location(request.getLocation())
                .build();

        bookCopyRepository.save(bookCopy);

        return BookCopyResponse.builder()
                .bookCopyId(bookCopy.getBookCopyId())
                .location(bookCopy.getLocation())
                .status(bookCopy.getStatus())
                .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                .build();
    }

    public BookCopyResponse getBookCopy(String bookCopyId) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKCOPY_NOT_FOUND));

        List<LoanRecord> loanRecords = loanRecordRepository.findByBookCopyId(bookCopyId);

        return BookCopyResponse.builder()
                .bookCopyId(bookCopy.getBookCopyId())
                .location(bookCopy.getLocation())
                .status(bookCopy.getLocation())
                .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                .loanRecords(new HashSet<>(loanRecords))
                .build();
    }

    public List<BookCopyResponse> getAllBookCopies() {
        return bookCopyRepository.findAll().stream()
                .map(bookCopy -> BookCopyResponse.builder()
                        .bookCopyId(bookCopy.getBookCopyId())
                        .location(bookCopy.getLocation())
                        .status(bookCopy.getLocation())
                        .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                        .loanRecords(new HashSet<>(loanRecordRepository.findByBookCopyId(bookCopy.getBookCopyId())))
                        .build())
                .collect(Collectors.toList());
    }

    public BookCopyResponse updateBookCopy(String bookCopyId, UpdateBookCopyRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKCOPY_NOT_FOUND));

        List<LoanRecord> loanRecords = loanRecordRepository.findByBookCopyId(bookCopyId);

        bookCopy.setStatus(request.getStatus());
        bookCopy.setLocation(request.getLocation());

        bookCopyRepository.save(bookCopy);

        return BookCopyResponse.builder()
                .bookCopyId(bookCopy.getBookCopyId())
                .location(bookCopy.getLocation())
                .status(bookCopy.getLocation())
                .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                .loanRecords(new HashSet<>(loanRecords))
                .build();
    }

    public void deleteBookCopy(String bookCopyId) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKCOPY_NOT_FOUND));

        if (bookCopyRepository.isBookCopyBorrowed(bookCopyId)) {
            throw new AppException(ErrorCode.BOOKCOPY_IS_BORROWED);
        }

        bookCopyRepository.delete(bookCopy);
    }

    public List<BookCopyResponse> getBookCopiesByBookTitle(String bookTitleId) {
        return bookCopyRepository.findByBookTitleId(bookTitleId).stream()
                .map(bookCopy -> BookCopyResponse.builder()
                        .bookCopyId(bookCopy.getBookCopyId())
                        .location(bookCopy.getLocation())
                        .status(bookCopy.getLocation())
                        .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                        .loanRecords(new HashSet<>(loanRecordRepository.findByBookCopyId(bookCopy.getBookCopyId())))
                        .build())
                .collect(Collectors.toList());
    }

    public List<BookCopyResponse> getBookCopiesByStatus(String status) {
        return bookCopyRepository.findByStatus(status).stream()
                .map(bookCopy -> BookCopyResponse.builder()
                        .bookCopyId(bookCopy.getBookCopyId())
                        .location(bookCopy.getLocation())
                        .status(bookCopy.getStatus())
                        .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                        .loanRecords(new HashSet<>(loanRecordRepository.findByBookCopyId(bookCopy.getBookCopyId())))
                        .build())
                .collect(Collectors.toList());
    }

    public List<BookCopyResponse> getBookCopiesByLocation(String location) {
        return bookCopyRepository.findByLocation(location).stream()
                .map(bookCopy -> BookCopyResponse.builder()
                        .bookCopyId(bookCopy.getBookCopyId())
                        .location(bookCopy.getLocation())
                        .status(bookCopy.getStatus())
                        .bookTitleId(bookCopy.getBookTitle().getBookTitleId())
                        .loanRecords(new HashSet<>(loanRecordRepository.findByBookCopyId(bookCopy.getBookCopyId())))
                        .build())
                .collect(Collectors.toList());
    }

}