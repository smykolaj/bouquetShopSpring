package org.example.finalproject.services;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.models.Bouquet;
import org.example.finalproject.repositories.BouquetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogueService {
    private final BouquetRepository bouquetRepo;
    public List<Bouquet> findAll() {
        List<Bouquet> bouquets = new ArrayList<>();
        for (Bouquet bouquet : bouquetRepo.findAll()) {
            bouquets.add(bouquet);
        }
        return bouquets;
    }
}