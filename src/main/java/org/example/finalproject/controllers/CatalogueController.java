package org.example.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.services.CatalogueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalogue")
public class CatalogueController
{
    private final CatalogueService catalogueService;

    @GetMapping
    public String list(Model m)
    {
        m.addAttribute("bouquets", catalogueService.findAll());
        return "catalogue";
    }
}
