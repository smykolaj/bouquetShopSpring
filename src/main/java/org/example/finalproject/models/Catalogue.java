package org.example.finalproject.models;


import jakarta.persistence.*;
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
public class Catalogue
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "catalogue")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BouquetCatalogue> bouquetCatalogues = new HashSet<>();


    @NotBlank
    @Size(min = 1, max = 50)
    private String name;


    @Size(max = 10, message = "Catalogue can have at most 10 tags")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ElementCollection
    @CollectionTable(name = "catalogue_tags", joinColumns = @JoinColumn(name = "catalogue_id"))
    private List<String> tags = new ArrayList<>();

}