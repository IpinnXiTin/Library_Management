package com.facenet.library_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String readerId;

    String fullName;

    LocalDate dateOfBirth;

    String phoneNumber;

    String email;

    @OneToMany(mappedBy = "reader")
    Set<LoanRecord> loanRecords;

}
