package com.facenet.library_management.controller;

import com.facenet.library_management.dto.request.CreateBookCopyRequest;
import com.facenet.library_management.dto.request.UpdateBookCopyRequest;
import com.facenet.library_management.dto.response.ApiResponse;
import com.facenet.library_management.dto.response.BookCopyResponse;
import com.facenet.library_management.service.BookCopyService;
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
@RequestMapping("/book-copies")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Book Copy Management", description = "APIs for managing book copies")
public class BookCopyController {

    BookCopyService bookCopyService;

    @Operation(summary = "Create new book copy")
    @PostMapping
    public ResponseEntity<ApiResponse<BookCopyResponse>> createNewBookCopy(
            @Valid @RequestBody CreateBookCopyRequest request
    ) {
        BookCopyResponse bookCopy = bookCopyService.createNewBookCopy(request);

        ApiResponse<BookCopyResponse> apiResponse = ApiResponse.<BookCopyResponse>builder()
                .code(1000)
                .message("BookCopy created successfully!")
                .data(bookCopy)
                .build();

        return ResponseEntity.status(201).body(apiResponse);
    }

    @Operation(summary = "Get book copy by ID")
    @GetMapping("/{bookCopyId}")
    public ResponseEntity<ApiResponse<BookCopyResponse>> getBookCopy(@PathVariable String bookCopyId) {
        BookCopyResponse bookCopy = bookCopyService.getBookCopy(bookCopyId);

        ApiResponse<BookCopyResponse> apiResponse = ApiResponse.<BookCopyResponse>builder()
                .code(1000)
                .message("BookCopy retrieved successfully!")
                .data(bookCopy)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all book copies")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookCopyResponse>>> getAllBookCopies() {
        List<BookCopyResponse> allBookCopies = bookCopyService.getAllBookCopies();

        ApiResponse<List<BookCopyResponse>> apiResponse = ApiResponse.<List<BookCopyResponse>>builder()
                .code(1000)
                .message("BookCopy list retrieved successfully!")
                .data(allBookCopies)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Update book copy")
    @PutMapping("/{bookCopyId}")
    public ResponseEntity<ApiResponse<BookCopyResponse>> updateBookCopy(
            @PathVariable String bookCopyId,
            @Valid @RequestBody UpdateBookCopyRequest request
    ) {
        BookCopyResponse updatedBookCopy = bookCopyService.updateBookCopy(bookCopyId, request);

        ApiResponse<BookCopyResponse> apiResponse = ApiResponse.<BookCopyResponse>builder()
                .code(1000)
                .message("BookCopy updated successfully!")
                .data(updatedBookCopy)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Delete book copy")
    @DeleteMapping("/{bookCopyId}")
    public ResponseEntity<ApiResponse<Void>> deleteBookCopy(@PathVariable String bookCopyId) {
        bookCopyService.deleteBookCopy(bookCopyId);

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(1000)
                .message("BookCopy deleted successfully!")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get book copies by book title ID")
    @GetMapping("/by-title/{bookTitleId}")
    public ResponseEntity<ApiResponse<List<BookCopyResponse>>> getBookCopiesByBookTitle(
            @PathVariable String bookTitleId) {

        List<BookCopyResponse> bookCopies = bookCopyService.getBookCopiesByBookTitle(bookTitleId);

        ApiResponse<List<BookCopyResponse>> response = ApiResponse.<List<BookCopyResponse>>builder()
                .code(1000)
                .message("Book copies retrieved successfully by bookTitleId")
                .data(bookCopies)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get book copies by status")
    @GetMapping("/by-status/{status}")
    public ResponseEntity<ApiResponse<List<BookCopyResponse>>> getBookCopiesByStatus(
            @PathVariable String status) {

        List<BookCopyResponse> bookCopies = bookCopyService.getBookCopiesByStatus(status);

        ApiResponse<List<BookCopyResponse>> response = ApiResponse.<List<BookCopyResponse>>builder()
                .code(1000)
                .message("Book copies retrieved successfully by status")
                .data(bookCopies)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get book copies by location")
    @GetMapping("/by-location/{location}")
    public ResponseEntity<ApiResponse<List<BookCopyResponse>>> getBookCopiesByLocation(
            @PathVariable String location) {

        List<BookCopyResponse> bookCopies = bookCopyService.getBookCopiesByLocation(location);

        ApiResponse<List<BookCopyResponse>> response = ApiResponse.<List<BookCopyResponse>>builder()
                .code(1000)
                .message("Book copies retrieved successfully by location")
                .data(bookCopies)
                .build();

        return ResponseEntity.ok(response);
    }

}