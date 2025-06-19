package com.facenet.library_management.service;

import com.facenet.library_management.dto.request.CreateLoanRecordRequest;
import com.facenet.library_management.dto.request.ReturnBookRequest;
import com.facenet.library_management.dto.response.LoanRecordResponse;
import com.facenet.library_management.entity.BookCopy;
import com.facenet.library_management.entity.LoanRecord;
import com.facenet.library_management.entity.Reader;
import com.facenet.library_management.enums.ErrorCode;
import com.facenet.library_management.exception.AppException;
import com.facenet.library_management.repository.BookCopyRepository;
import com.facenet.library_management.repository.LoanRecordRepository;
import com.facenet.library_management.repository.ReaderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoanRecordService {

    LoanRecordRepository loanRecordRepository;
    ReaderRepository readerRepository;
    BookCopyRepository bookCopyRepository;

    public LoanRecordResponse createNewLoanRecord(CreateLoanRecordRequest request) {
        Reader reader = readerRepository.findById(request.getReaderId())
                .orElseThrow(() -> new AppException(ErrorCode.READER_NOT_FOUND));

        BookCopy bookCopy = bookCopyRepository.findById(request.getBookCopyId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKCOPY_NOT_FOUND));

        LoanRecord loanRecord = LoanRecord.builder()
                .loanRecordId(request.getLoanRecordId())
                .reader(reader)
                .bookCopy(bookCopy)
                .expectedReturnDate(request.getExpectedReturnDate())
                .build();

        loanRecordRepository.save(loanRecord);
        return toLoanRecordResponse(loanRecord);
    }

    public LoanRecordResponse getLoanRecord(String loanRecordId) {
        LoanRecord loanRecord = loanRecordRepository.findById(loanRecordId)
                .orElseThrow(() -> new AppException(ErrorCode.LOAN_RECORD_NOT_FOUND));

        return toLoanRecordResponse(loanRecord);
    }

    public List<LoanRecordResponse> getAllLoanRecords() {
        return loanRecordRepository.findAll().stream()
                .map(this::toLoanRecordResponse)
                .collect(Collectors.toList());
    }

    public LoanRecordResponse returnBook(String loanRecordId, ReturnBookRequest request) {
        LoanRecord loanRecord = loanRecordRepository.findById(loanRecordId)
                .orElseThrow(() -> new AppException(ErrorCode.LOAN_RECORD_NOT_FOUND));

        loanRecord.setActualReturnDate(request.getActualReturnDate());
        loanRecord.setNote(request.getNote());

        loanRecordRepository.save(loanRecord);

        return toLoanRecordResponse(loanRecord);
    }

    public void deleteLoanRecord(String loanRecordId) {
        LoanRecord loanRecord = loanRecordRepository.findById(loanRecordId)
                .orElseThrow(() -> new AppException(ErrorCode.LOAN_RECORD_NOT_FOUND));

        loanRecordRepository.delete(loanRecord);
    }

    private LoanRecordResponse toLoanRecordResponse(LoanRecord loanRecord) {
        return LoanRecordResponse.builder()
                .loanRecordId(loanRecord.getLoanRecordId())
                .reader(loanRecord.getReader())
                .bookCopy(loanRecord.getBookCopy())
                .expectedReturnDate(loanRecord.getExpectedReturnDate())
                .actualReturnDate(loanRecord.getActualReturnDate())
                .note(loanRecord.getNote())
                .build();
    }
}
