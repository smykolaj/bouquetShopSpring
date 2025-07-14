package org.example.finalproject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accessory
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "accessory")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccessoryBouquet> accessoryBouquets = new HashSet<>();


    @NotBlank
    @Size(min = 1, max = 50)
    private String color;

    @NotBlank
    @Size(min = 1, max = 50)
    private String size;

    @NotNull
    private double price;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Transient
    public String getCode()
    {
        return size + color + name;
    }
}
