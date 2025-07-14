package org.example.finalproject.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.finalproject.models.Bouquet;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
@RequiredArgsConstructor
@SessionScope
public class Cart
{

    public final Map<Bouquet, Integer> items = new HashMap<>();

    public void add(Bouquet b, int qty)
    {
        boolean contains = false;
        b.getFlowerBouquets().size();
        for (Bouquet bouquet : items.keySet())
        {
            if (bouquet.getId().equals(b.getId()))
            {
                items.put(bouquet, items.get(bouquet) + qty);
                contains = true;
            }
        }
        if (!contains)
        {
            items.put(b, qty);
        }
    }


    public double total()
    {
        double total = 0;

        for (Map.Entry<Bouquet, Integer> entry : items.entrySet())
        {
            Bouquet bouquet = entry.getKey();
            int qty = entry.getValue();

            total += bouquet.getTotalPrice() * qty;
        }

        return total;
    }

    public void clear()
    {
        items.clear();
    }

}