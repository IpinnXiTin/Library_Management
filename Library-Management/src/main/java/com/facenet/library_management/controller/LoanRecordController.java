package com.facenet.library_management.controller;

import com.facenet.library_management.dto.request.CreateLoanRecordRequest;
import com.facenet.library_management.dto.request.ReturnBookRequest;

import com.facenet.library_management.dto.response.ApiResponse;
import com.facenet.library_management.dto.response.LoanRecordResponse;
import com.facenet.library_management.service.LoanRecordService;
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
@RequestMapping("/loan-records")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Loan Record Management", description = "APIs for managing book loans and returns")
public class LoanRecordController {

    LoanRecordService loanRecordService;

    @Operation(summary = "Create new loan record (Borrow book)")
    @PostMapping
    public ResponseEntity<ApiResponse<LoanRecordResponse>> createLoan(
            @Valid @RequestBody CreateLoanRecordRequest request
    ) {
        LoanRecordResponse loanRecord = loanRecordService.createNewLoanRecord(request);

        ApiResponse<LoanRecordResponse> apiResponse = ApiResponse.<LoanRecordResponse>builder()
                .code(1000)
                .message("Book borrowed successfully!")
                .data(loanRecord)
                .build();

        return ResponseEntity.status(201).body(apiResponse);
    }

    @Operation(summary = "Get loan record by ID")
    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse<LoanRecordResponse>> getLoanRecord(@PathVariable String loanId) {
        LoanRecordResponse loanRecord = loanRecordService.getLoanRecord(loanId);

        ApiResponse<LoanRecordResponse> apiResponse = ApiResponse.<LoanRecordResponse>builder()
                .code(1000)
                .message("Loan record retrieved successfully!")
                .data(loanRecord)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all loan records")
    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanRecordResponse>>> getAllLoanRecords() {
        List<LoanRecordResponse> loanRecords = loanRecordService.getAllLoanRecords();

        ApiResponse<List<LoanRecordResponse>> apiResponse = ApiResponse.<List<LoanRecordResponse>>builder()
                .code(1000)
                .message("Loan records retrieved successfully!")
                .data(loanRecords)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Return book")
    @PatchMapping("/{loanId}/return")
    public ResponseEntity<ApiResponse<LoanRecordResponse>> returnBook(
            @PathVariable String loanId,
            @Valid @RequestBody ReturnBookRequest request
    ) {
        LoanRecordResponse loanRecord = loanRecordService.returnBook(loanId, request);

        ApiResponse<LoanRecordResponse> apiResponse = ApiResponse.<LoanRecordResponse>builder()
                .code(1000)
                .message("Book returned successfully!")
                .data(loanRecord)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}