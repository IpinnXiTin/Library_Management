package com.facenet.library_management.controller;

import com.facenet.library_management.dto.request.CreateBookTitleRequest;
import com.facenet.library_management.dto.request.UpdateBookTitleRequest;
import com.facenet.library_management.dto.response.ApiResponse;
import com.facenet.library_management.dto.response.BookTitleResponse;
import com.facenet.library_management.service.BookTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-titles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Book Title Management", description = "APIs for managing book titles")
public class BookTitleController {

    BookTitleService bookTitleService;

    @Operation(summary = "Create new book title")
    @PostMapping
    public ResponseEntity<ApiResponse<BookTitleResponse>> createBookTitle(
            @Valid @RequestBody CreateBookTitleRequest request
    ) {
        BookTitleResponse bookTitle = bookTitleService.createNewBookTitle(request);

        ApiResponse<BookTitleResponse> apiResponse = ApiResponse.<BookTitleResponse>builder()
                .code(1000)
                .message("Book title created successfully!")
                .data(bookTitle)
                .build();

        return ResponseEntity.status(201).body(apiResponse);
    }

    @Operation(summary = "Get book title by ID")
    @GetMapping("/{bookTitleId}")
    public ResponseEntity<ApiResponse<BookTitleResponse>> getBookTitle(@PathVariable String bookTitleId) {
        BookTitleResponse bookTitle = bookTitleService.getBookTitle(bookTitleId);

        ApiResponse<BookTitleResponse> apiResponse = ApiResponse.<BookTitleResponse>builder()
                .code(1000)
                .message("Book title retrieved successfully!")
                .data(bookTitle)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all book titles")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookTitleResponse>>> getAllBookTitles() {
        List<BookTitleResponse> bookTitles = bookTitleService.getAllBookTitles();

        ApiResponse<List<BookTitleResponse>> apiResponse = ApiResponse.<List<BookTitleResponse>>builder()
                .code(1000)
                .message("Book titles retrieved successfully!")
                .data(bookTitles)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Update book title")
    @PutMapping("/{bookTitleId}")
    public ResponseEntity<ApiResponse<BookTitleResponse>> updateBookTitle(
            @PathVariable String bookTitleId,
            @Valid @RequestBody UpdateBookTitleRequest request
    ) {
        BookTitleResponse updatedBookTitle = bookTitleService.updateBookTitle(bookTitleId, request);

        ApiResponse<BookTitleResponse> apiResponse = ApiResponse.<BookTitleResponse>builder()
                .code(1000)
                .message("Book title updated successfully!")
                .data(updatedBookTitle)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Delete book title")
    @DeleteMapping("/{bookTitleId}")
    public ResponseEntity<ApiResponse<Void>> deleteBookTitle(@PathVariable String bookTitleId) {
        bookTitleService.deleteBookTitle(bookTitleId);

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(1000)
                .message("Book title deleted successfully!")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Search book titles by title")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookTitleResponse>>> searchBookTitles(
            @RequestParam String title
    ) {
        List<BookTitleResponse> searchResults = bookTitleService.searchBookTitlesByTitle(title);

        if (searchResults.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        ApiResponse<List<BookTitleResponse>> apiResponse = ApiResponse.<List<BookTitleResponse>>builder()
                .code(1000)
                .message("Book titles found successfully!")
                .data(searchResults)
                .build();

        return ResponseEntity.ok(apiResponse);
    }


    @Operation(summary = "Get book titles by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<BookTitleResponse>>> getBookTitlesByCategory(
            @PathVariable String category
    ) {
        List<BookTitleResponse> bookTitles = bookTitleService.getBookTitlesByCategory(category);

        ApiResponse<List<BookTitleResponse>> apiResponse = ApiResponse.<List<BookTitleResponse>>builder()
                .code(1000)
                .message("Book titles by category retrieved successfully!")
                .data(bookTitles)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get book titles by author")
    @GetMapping("/author/{author}")
    public ResponseEntity<ApiResponse<List<BookTitleResponse>>> getBookTitlesByAuthor(
            @PathVariable String author
    ) {
        List<BookTitleResponse> bookTitles = bookTitleService.getBookTitlesByAuthor(author);

        ApiResponse<List<BookTitleResponse>> apiResponse = ApiResponse.<List<BookTitleResponse>>builder()
                .code(1000)
                .message("Book titles by author retrieved successfully!")
                .data(bookTitles)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}