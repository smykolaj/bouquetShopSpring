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
public class Bouquet
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "bouquet", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<AccessoryBouquet> accessoryBouquets = new HashSet<>();

    @OneToMany(mappedBy = "bouquet")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<BouquetCatalogue> bouquetCatalogues = new HashSet<>();

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Min(0)
    @Builder.Default
    private double basePrice = 100d;

    private String description;

    @OneToMany(mappedBy = "bouquet")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<OrderBouquet> orderBouquets = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "bouquet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FlowerBouquet> flowerBouquets = new ArrayList<>();

    @Transient
    public double getTotalPrice()
    {
        var a = 2;
        double totalPrice = 0;
        totalPrice += basePrice;
        for (FlowerBouquet flowerBouquet : flowerBouquets)
        {
            totalPrice += flowerBouquet.getFlower().getPricePerStem() * flowerBouquet.getQuantity();
        }
        for (AccessoryBouquet accessoryBouquet : accessoryBouquets)
        {
            totalPrice += accessoryBouquet.getAccessory().getPrice() * accessoryBouquet.getQuantity();
        }
        return totalPrice;
    }

}
