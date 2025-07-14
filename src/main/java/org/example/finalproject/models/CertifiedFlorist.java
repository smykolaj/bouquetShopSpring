package org.example.finalproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CertifiedFlorist extends Employee
{


    @NotBlank
    @Size(min = 1, max = 50)
    private String certificationId;
    @NotNull
    private LocalDate certificationDate;
    @NotBlank
    @Size(min = 1, max = 50)
    private String issuedBy;
    @NotBlank
    @Size(min = 1, max = 50)
    private String floristLevel;
}
