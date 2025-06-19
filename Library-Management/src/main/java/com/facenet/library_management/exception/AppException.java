package com.facenet.library_management.exception;

import com.facenet.library_management.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AppException extends RuntimeException {

    ErrorCode errorCode;
}
