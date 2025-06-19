package com.facenet.library_management.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReaderRequest {

    String fullName;

    LocalDate dateOfBirth;

    String phoneNumber;

    String email;
}
