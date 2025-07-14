package org.example.finalproject.models;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Student extends Employee
{

    private LocalDate startedWorking;
    private LocalDate dateOfBirth;

    public CertifiedFlorist certifyStudent(String certId,
                                           LocalDate certDate,
                                           String issuer,
                                           String level)
    {
        return CertifiedFlorist.builder()
                .name(getName())
                .pesel(getPesel())
                .phoneNumber(getPhoneNumber())
                .roles(getRoles())
                .drawerNumber(getDrawerNumber())
                .drivingLicense(getDrivingLicense())
                .specialization(level)
                .certificationId(certId)
                .certificationDate(certDate)
                .issuedBy(issuer)
                .floristLevel(level)
                .build();
    }
}
