package org.example.finalproject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.experimental.SuperBuilder;
import lombok.*;
import org.example.finalproject.constraints.RoleFieldsValid;
import org.example.finalproject.enums.EmployeeRole;
import org.example.finalproject.enums.OrderStatus;
import org.example.finalproject.enums.PaymentMethod;
import org.example.finalproject.enums.PaymentStatus;

import java.util.EnumSet;
import java.util.Set;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "pesel"))
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@RoleFieldsValid
public abstract class Employee
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Column(length = 11, nullable = false)
    @Size(min = 11, max = 11)
    private String pesel;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = EmployeeRole.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_roles", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "role")
    @Size(min = 1)
    private Set<EmployeeRole> roles = EnumSet.noneOf(EmployeeRole.class);

    private Integer drawerNumber;
    private String drivingLicense;
    private String specialization;

    protected void requireRole(EmployeeRole role)
    {
        if (!roles.contains(role))
        {
            throw new IllegalStateException("Operation requires role " + role);
        }
    }

    public void acceptPayment(Double amount, Order order)
    {
        requireRole(EmployeeRole.CASHIER);

        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .method(PaymentMethod.CASH)
                .status(PaymentStatus.COMPLETED)
                .build();

        order.setStatus(OrderStatus.PAID);
        order.setPayment(payment);
        order.getCustomer().increaseLoyalty();

    }

    public void assembleBouquet(Bouquet bouquet, Order order)
    {
        requireRole(EmployeeRole.FLORIST);
        order.setStatus(OrderStatus.PREPARED);
    }

    public void deliverOrder(Order order)
    {
        requireRole(EmployeeRole.COURIER);
        order.setStatus(OrderStatus.DELIVERED);
    }
}