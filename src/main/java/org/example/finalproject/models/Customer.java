package org.example.finalproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @Min(0)
    private int loyaltyPoints = 0;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    public void increaseLoyalty()
    {
        loyaltyPoints++;
    }

    public boolean redeemLoyaltyPoints()
    {
        if (loyaltyPoints < 10) return false;
        loyaltyPoints -= 10;
        return true;
    }
}
