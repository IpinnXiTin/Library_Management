package com.facenet.library_management.service;

import com.facenet.library_management.dto.request.CreateBookTitleRequest;
import com.facenet.library_management.dto.request.UpdateBookTitleRequest;
import com.facenet.library_management.dto.response.BookCopyResponse;
import com.facenet.library_management.dto.response.BookTitleResponse;
import com.facenet.library_management.entity.BookCopy;
import com.facenet.library_management.entity.BookTitle;
import com.facenet.library_management.exception.AppException;
import com.facenet.library_management.enums.ErrorCode;
import com.facenet.library_management.repository.BookCopyRepository;
import com.facenet.library_management.repository.BookTitleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookTitleService {

    BookTitleRepository bookTitleRepository;
    BookCopyRepository bookCopyRepository;

    public BookTitleResponse createNewBookTitle(CreateBookTitleRequest request) {
        if (bookTitleRepository.existsByBookTitleId(request.getBookTitleId())) {
            throw new AppException(ErrorCode.BOOKTITLE_EXISTED);
        }

        BookTitle bookTitle = BookTitle.builder()
                .bookTitleId(request.getBookTitleId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .category(request.getCategory())
                .publisher(request.getPublisher())
                .pageCount(request.getPageCount())
                .publicationYear(request.getPublicationYear())
                .build();

        bookTitleRepository.save(bookTitle);

        return BookTitleResponse.builder()
                .bookTitleId(request.getBookTitleId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .category(request.getCategory())
                .publisher(request.getPublisher())
                .pageCount(request.getPageCount())
                .publicationYear(request.getPublicationYear())
                .build();
    }

    public BookTitleResponse getBookTitle(String bookTitleId) {
        BookTitle bookTitle = bookTitleRepository.findById(bookTitleId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKTITLE_NOT_FOUND));

        List<BookCopy> bookCopies = bookCopyRepository.findByBookTitleId(bookTitleId);
        Set<BookCopyResponse> bookCopyResponses = mapToBookCopyResponses(bookCopies);

        return mapToBookTitleResponse(bookTitle, bookCopyResponses);
    }

    public List<BookTitleResponse> getAllBookTitles() {
        return bookTitleRepository.findAll().stream()
                .map(bookTitle -> {
                    List<BookCopy> copies = bookCopyRepository.findByBookTitleId(bookTitle.getBookTitleId());
                    return mapToBookTitleResponse(bookTitle, mapToBookCopyResponses(copies));
                })
                .collect(Collectors.toList());
    }

    public BookTitleResponse updateBookTitle(String bookTitleId, UpdateBookTitleRequest request) {
        BookTitle bookTitle = bookTitleRepository.findById(bookTitleId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKTITLE_NOT_FOUND));

        bookTitle.setTitle(request.getTitle());
        bookTitle.setAuthor(request.getAuthor());
        bookTitle.setCategory(request.getCategory());
        bookTitle.setPublisher(request.getPublisher());
        bookTitle.setPageCount(request.getPageCount());
        bookTitle.setPublicationYear(request.getPublicationYear());

        bookTitleRepository.save(bookTitle);

        List<BookCopy> copies = bookCopyRepository.findByBookTitleId(bookTitleId);
        return mapToBookTitleResponse(bookTitle, mapToBookCopyResponses(copies));
    }

    public void deleteBookTitle(String bookTitleId) {
        BookTitle bookTitle = bookTitleRepository.findById(bookTitleId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKTITLE_NOT_FOUND));

        if (bookTitleRepository.hasBookCopies(bookTitleId)) {
            throw new AppException(ErrorCode.BOOKTITLE_HAS_COPIES);
        }

        bookTitleRepository.delete(bookTitle);
    }

    public List<BookTitleResponse> searchBookTitlesByTitle(String title) {
        return bookTitleRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(bookTitle -> {
                    List<BookCopy> copies = bookCopyRepository.findByBookTitleId(bookTitle.getBookTitleId());
                    return mapToBookTitleResponse(bookTitle, mapToBookCopyResponses(copies));
                })
                .collect(Collectors.toList());
    }

    public List<BookTitleResponse> getBookTitlesByCategory(String category) {
        return bookTitleRepository.findByCategory(category).stream()
                .map(bookTitle -> {
                    List<BookCopy> copies = bookCopyRepository.findByBookTitleId(bookTitle.getBookTitleId());
                    return mapToBookTitleResponse(bookTitle, mapToBookCopyResponses(copies));
                })
                .collect(Collectors.toList());
    }

    public List<BookTitleResponse> getBookTitlesByAuthor(String author) {
        return bookTitleRepository.findByAuthor(author).stream()
                .map(bookTitle -> {
                    List<BookCopy> copies = bookCopyRepository.findByBookTitleId(bookTitle.getBookTitleId());
                    return mapToBookTitleResponse(bookTitle, mapToBookCopyResponses(copies));
                })
                .collect(Collectors.toList());
    }

    // --- Private helper methods ---

    private Set<BookCopyResponse> mapToBookCopyResponses(List<BookCopy> copies) {
        return copies.stream()
                .map(copy -> BookCopyResponse.builder()
                        .bookCopyId(copy.getBookCopyId())
                        .location(copy.getLocation())
                        .status(copy.getStatus())
                        .bookTitleId(copy.getBookTitle().getBookTitleId())
                        .build())
                .collect(Collectors.toSet());
    }

    private BookTitleResponse mapToBookTitleResponse(BookTitle bookTitle, Set<BookCopyResponse> bookCopies) {
        return BookTitleResponse.builder()
                .bookTitleId(bookTitle.getBookTitleId())
                .title(bookTitle.getTitle())
                .author(bookTitle.getAuthor())
                .category(bookTitle.getCategory())
                .publisher(bookTitle.getPublisher())
                .pageCount(bookTitle.getPageCount())
                .publicationYear(bookTitle.getPublicationYear())
                .bookCopies(bookCopies)
                .build();
    }
}
