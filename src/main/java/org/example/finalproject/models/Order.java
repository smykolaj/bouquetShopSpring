package org.example.finalproject.models;


import jakarta.persistence.*;
import lombok.*;
import org.example.finalproject.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Orders")
public class Order
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime dateCreated = LocalDateTime.now();

    private String specialPreference;
    private String deliveryAddress;

    private double deliveryBasePrice = 25d;

    private LocalDateTime deliveryDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @ManyToOne(optional = false)
    private Customer customer;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderBouquet> orderBouquets = new HashSet<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Review review;


    public double getPrice() {
        double bouquetsTotal = orderBouquets.stream()
                .mapToDouble(ob -> ob.getBouquet().getTotalPrice() * ob.getQuantity())
                .sum();

        boolean isDelivery = deliveryAddress != null && !deliveryAddress.isBlank();

        return bouquetsTotal + (isDelivery ? deliveryBasePrice : 0);
    }



    public void applyLoyaltyDiscount()
    {
        this.deliveryBasePrice = 0;
    }
}