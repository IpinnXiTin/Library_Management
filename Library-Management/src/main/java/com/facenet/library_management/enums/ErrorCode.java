package com.facenet.library_management.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public enum ErrorCode {

    BOOKTITLE_EXISTED(1001, "Booktitle existed", HttpStatus.BAD_REQUEST),
    BOOKTITLE_NOT_FOUND(1002, "Booktitle not found", HttpStatus.BAD_REQUEST),
    BOOKTITLE_HAS_COPIES(1003, "Booktitle has copies", HttpStatus.BAD_REQUEST),
    BOOKCOPY_EXISTED(1101, "BookCopy existed", HttpStatus.BAD_REQUEST),
    BOOKCOPY_NOT_FOUND(1102, "Bookcopy not found", HttpStatus.BAD_REQUEST),
    BOOKCOPY_IS_BORROWED(1103, "Bookcopy is borrowed", HttpStatus.BAD_REQUEST),
    READER_EXISTED(1201, "Reader existed", HttpStatus.BAD_REQUEST),
    READER_PHONE_EXISTED(1202, "Reader's phone number existed", HttpStatus.BAD_REQUEST),
    READER_NOT_FOUND(1203, "Reader not found", HttpStatus.BAD_REQUEST),
    LOAN_RECORD_NOT_FOUND(1301, "Loan record not found", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatus httpStatus;
}
