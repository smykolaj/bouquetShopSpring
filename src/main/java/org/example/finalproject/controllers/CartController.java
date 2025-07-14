package org.example.finalproject.controllers;

import lombok.RequiredArgsConstructor;
import org.example.finalproject.enums.OrderStatus;
import org.example.finalproject.models.Bouquet;
import org.example.finalproject.models.Customer;
import org.example.finalproject.models.Order;
import org.example.finalproject.models.OrderBouquet;
import org.example.finalproject.repositories.BouquetRepository;
import org.example.finalproject.repositories.CustomerRepository;
import org.example.finalproject.repositories.OrderRepository;
import org.example.finalproject.services.Cart;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
@SessionAttributes("order")
public class CartController
{

    @ModelAttribute("order")
    public Order order()
    {
        return new Order();
    }

    private final Cart cart;
    private final BouquetRepository bouquetRepo;
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;


    @GetMapping
    public String view(Model m)
    {
        m.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable Long id, @RequestParam int qty)
    {
        Bouquet b = bouquetRepo.findById(id).orElseThrow();
        System.out.println(b);
        cart.add(b, qty);
        return "redirect:/catalogue";

    }

    @PostMapping("/change")
    public String changeQty(@RequestParam Long id, @RequestParam int change, @RequestParam int currentQty)
    {
        int newQty = currentQty + change;

        if (newQty < 1)
        {
            cart.getItems().keySet().removeIf(b -> b.getId().equals(id));
        } else
        {
            for (Bouquet b : cart.getItems().keySet())
            {
                if (b.getId().equals(id))
                {
                    cart.getItems().put(b, newQty);
                    break;
                }
            }
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id)
    {
        cart.getItems().keySet().removeIf(bouquet -> bouquet.getId().equals(id));
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("preference") String preference,
                           @ModelAttribute("order") Order order,
                           Model model)
    {
        Customer user = customerRepo.findFirstByOrderByIdAsc().orElseThrow();


        order.setCustomer(user);
        order.setSpecialPreference(preference);
        System.out.println(preference);

        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        return "checkout";
    }


    @PostMapping("/checkout/confirm")
    public String confirm(
            @RequestParam String deliveryType,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String number,
            @RequestParam("deliveryDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveryDate,
            @RequestParam(name = "redeem", defaultValue = "false") boolean redeem,
            @RequestParam(name = "preference", required = false) String preference,
            Model model) {

        Customer user = customerRepo.findFirstByOrderByIdAsc().orElseThrow();

        Order order = new Order();
        order.setCustomer(user);
        order.setSpecialPreference(preference);
        order.setDeliveryDate(deliveryDate);
        order.setStatus(OrderStatus.PENDING);

        if (!"pickup".equals(deliveryType)) {
            order.setDeliveryAddress(city + " " + street + " " + number);
            if (redeem) {
                user.redeemLoyaltyPoints();
                order.applyLoyaltyDiscount();
            }
        }

        System.out.println(cart.getItems().size());

        for (Map.Entry<Bouquet, Integer> entry : cart.getItems().entrySet()) {
            OrderBouquet ob = OrderBouquet.builder()
                    .bouquet(entry.getKey())
                    .order(order)
                    .quantity(entry.getValue())
                    .dateMade(LocalDateTime.now())
                    .build();
            System.out.println(ob);
            order.getOrderBouquets().add(ob);
        }

        orderRepo.save(order);

        cart.clear();
        return "redirect:/payment/" + order.getId();
    }

}
