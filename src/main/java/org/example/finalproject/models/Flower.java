package org.example.finalproject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flower
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "flower")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<FlowerBouquet> flowerBouquets = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Size(max = 5, message = "Flower can have at most 5 colors")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ElementCollection
    @Builder.Default
    @CollectionTable(name = "flower_colors", joinColumns = @JoinColumn(name = "flower_id"))
    private List<String> colors = new ArrayList<>();

    @Min(0)
    private double pricePerStem;
}