package com.facenet.library_management.service;

import com.facenet.library_management.dto.request.CreateReaderRequest;
import com.facenet.library_management.dto.request.UpdateReaderRequest;
import com.facenet.library_management.dto.response.ReaderResponse;
import com.facenet.library_management.entity.Reader;
import com.facenet.library_management.enums.ErrorCode;
import com.facenet.library_management.exception.AppException;
import com.facenet.library_management.repository.LoanRecordRepository;
import com.facenet.library_management.repository.ReaderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReaderService {

    ReaderRepository readerRepository;
    LoanRecordRepository loanRecordRepository;

    public ReaderResponse createReader(CreateReaderRequest request) {

        if (readerRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.READER_EXISTED);
        }

        if (request.getPhoneNumber() != null && readerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.READER_PHONE_EXISTED);
        }

        Reader reader = Reader.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .build();

        readerRepository.save(reader);

        return toReaderResponse(reader);
    }

    public ReaderResponse getReader(String readerId) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new AppException(ErrorCode.READER_NOT_FOUND));

        return toReaderResponse(reader);
    }

    public List<ReaderResponse> getAllReaders() {
        return readerRepository.findAll().stream()
                .map(this::toReaderResponse)
                .collect(Collectors.toList());
    }

    public ReaderResponse updateReader(String readerId, UpdateReaderRequest request) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new AppException(ErrorCode.READER_NOT_FOUND));

        reader.setFullName(request.getFullName());
        reader.setEmail(request.getEmail());
        reader.setPhoneNumber(request.getPhoneNumber());
        reader.setDateOfBirth(request.getDateOfBirth());

        readerRepository.save(reader);

        return toReaderResponse(reader);
    }

    public void deleteReader(String readerId) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new AppException(ErrorCode.READER_NOT_FOUND));

        readerRepository.delete(reader);
    }

    public List<ReaderResponse> searchReaders(String name) {
        return readerRepository.findByReaderName(name).stream()
                .map(this::toReaderResponse)
                .collect(Collectors.toList());
    }

    private ReaderResponse toReaderResponse(Reader reader) {
        return ReaderResponse.builder()
                .readerId(reader.getReaderId())
                .fullName(reader.getFullName())
                .email(reader.getEmail())
                .phoneNumber(reader.getPhoneNumber())
                .dateOfBirth(reader.getDateOfBirth())
                .loanRecords(new HashSet<>(loanRecordRepository.findByReaderId(reader.getReaderId())))
                .build();
    }
}

