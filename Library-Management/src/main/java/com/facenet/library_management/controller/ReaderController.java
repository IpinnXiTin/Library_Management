package com.facenet.library_management.controller;

import com.facenet.library_management.dto.request.CreateReaderRequest;
import com.facenet.library_management.dto.request.UpdateReaderRequest;
import com.facenet.library_management.dto.response.ApiResponse;
import com.facenet.library_management.dto.response.ReaderResponse;
import com.facenet.library_management.service.ReaderService;
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
@RequestMapping("/readers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Reader Management", description = "APIs for managing readers/members")
public class ReaderController {

    ReaderService readerService;

    @Operation(summary = "Register new reader")
    @PostMapping
    public ResponseEntity<ApiResponse<ReaderResponse>> createReader(
            @Valid @RequestBody CreateReaderRequest request
    ) {
        ReaderResponse reader = readerService.createReader(request);

        ApiResponse<ReaderResponse> apiResponse = ApiResponse.<ReaderResponse>builder()
                .code(1000)
                .message("Reader registered successfully!")
                .data(reader)
                .build();

        return ResponseEntity.status(201).body(apiResponse);
    }

    @Operation(summary = "Get reader by ID")
    @GetMapping("/{readerId}")
    public ResponseEntity<ApiResponse<ReaderResponse>> getReader(@PathVariable String readerId) {
        ReaderResponse reader = readerService.getReader(readerId);

        ApiResponse<ReaderResponse> apiResponse = ApiResponse.<ReaderResponse>builder()
                .code(1000)
                .message("Reader retrieved successfully!")
                .data(reader)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all readers")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReaderResponse>>> getAllReaders() {
        List<ReaderResponse> readers = readerService.getAllReaders();

        ApiResponse<List<ReaderResponse>> apiResponse = ApiResponse.<List<ReaderResponse>>builder()
                .code(1000)
                .message("Readers retrieved successfully!")
                .data(readers)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Update reader information")
    @PutMapping("/{readerId}")
    public ResponseEntity<ApiResponse<ReaderResponse>> updateReader(
            @PathVariable String readerId,
            @Valid @RequestBody UpdateReaderRequest request
    ) {
        ReaderResponse updatedReader = readerService.updateReader(readerId, request);

        ApiResponse<ReaderResponse> apiResponse = ApiResponse.<ReaderResponse>builder()
                .code(1000)
                .message("Reader updated successfully!")
                .data(updatedReader)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Delete reader")
    @DeleteMapping("/{readerId}")
    public ResponseEntity<ApiResponse<Void>> deleteReader(@PathVariable String readerId) {
        readerService.deleteReader(readerId);

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(1000)
                .message("Reader deleted successfully!")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Search readers")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ReaderResponse>>> searchReaders(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        List<ReaderResponse> searchResults = readerService.searchReaders(name);

        ApiResponse<List<ReaderResponse>> apiResponse = ApiResponse.<List<ReaderResponse>>builder()
                .code(1000)
                .message("Reader search results retrieved successfully!")
                .data(searchResults)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}