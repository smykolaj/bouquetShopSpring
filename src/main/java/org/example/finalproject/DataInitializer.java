package org.example.finalproject;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.finalproject.enums.*;
import org.example.finalproject.models.*;
import org.example.finalproject.repositories.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final EmployeeRepository            employeeRepo;
    private final StudentRepository             studentRepo;
    private final CertifiedFloristRepository    floristRepo;
    private final CustomerRepository            customerRepo;
    private final CatalogueRepository           catalogueRepo;
    private final BouquetRepository             bouquetRepo;
    private final BouquetCatalogueRepository    bouquetCatalogueRepo;
    private final FlowerRepository              flowerRepo;
    private final FlowerBouquetRepository       fbRepo;
    private final AccessoryRepository           accessoryRepo;
    private final AccessoryBouquetRepository    accessoryBouquetRepo;
    private final OrderRepository               orderRepo;
    private final EmployeeOrderRepository       employeeOrderRepo;

    private Catalogue wedding, everyday;
    private Bouquet romanticSurprise, springJoy;
    private Accessory ribbon, burlap;
    private Student student;
    private CertifiedFlorist florist;

    @EventListener
    @Transactional
    public void onRefresh(ContextRefreshedEvent e) {

        if (employeeRepo.count()   == 0) initEmployees();
        if (catalogueRepo.count()  == 0) initCataloguesAccessories();
        if (flowerRepo.count()     == 0) initFlowersBouquets();
        if (bouquetCatalogueRepo.count() == 0) linkBouquetsToCatalogues();
        if (accessoryBouquetRepo.count() == 0) linkAccessoriesToBouquets();
        if (customerRepo.count()   == 0) initCustomersOrders();

        log.info("Sample data loaded");
    }

    private void initEmployees() {

        student = Student.builder()
                .name("Marta Student")
                .pesel("90101012345")
                .phoneNumber("501444333")
                .roles(EnumSet.of(EmployeeRole.FLORIST))
                .specialization("Wedding")
                .startedWorking(LocalDate.now().minusMonths(2))
                .dateOfBirth(LocalDate.of(1990, 10, 10))
                .build();
        studentRepo.save(student);

        florist = CertifiedFlorist.builder()
                .name("Pavlo Florist")
                .pesel("88050567890")
                .phoneNumber("503222111")
                .roles(EnumSet.of(EmployeeRole.FLORIST, EmployeeRole.CASHIER, EmployeeRole.COURIER))
                .specialization("Birthday")
                .drawerNumber(1)
                .drivingLicense("Pasha Vadila")
                .certificationId("FL-001")
                .certificationDate(LocalDate.now().minusYears(3))
                .issuedBy("International Floral Inst.")
                .floristLevel("Master")
                .build();
        floristRepo.save(florist);
    }

    private void initCataloguesAccessories() {

        wedding = Catalogue.builder()
                .name("Wedding")
                .tags(List.of("white", "romantic"))
                .build();

        everyday = Catalogue.builder()
                .name("Everyday")
                .tags(List.of("colourful", "budget"))
                .build();

        ribbon = Accessory.builder()
                .name("Silk Ribbon").color("Ivory").size("2 m").price(5.50)
                .build();
        burlap = Accessory.builder()
                .name("Burlap Roll").color("Brown").size("3 m").price(4.20)
                .build();

        catalogueRepo.saveAll(List.of(wedding, everyday));
        accessoryRepo.saveAll(List.of(ribbon, burlap));
    }

    private void initFlowersBouquets()
    {

        Flower rose        = Flower.builder().name("Rose")        .colors(List.of("Red", "White")).pricePerStem(5.0).build();
        Flower tulip       = Flower.builder().name("Tulip")       .colors(List.of("Yellow", "Pink")).pricePerStem(3.5).build();
        Flower lily        = Flower.builder().name("Lily")        .colors(List.of("White")).pricePerStem(4.2).build();
        Flower orchid      = Flower.builder().name("Orchid")      .colors(List.of("Purple", "White")).pricePerStem(7.0).build();
        Flower sunflower   = Flower.builder().name("Sunflower")   .colors(List.of("Yellow")).pricePerStem(2.8).build();
        Flower carnation   = Flower.builder().name("Carnation")   .colors(List.of("Pink", "Red")).pricePerStem(3.0).build();
        Flower daisy       = Flower.builder().name("Daisy")       .colors(List.of("White")).pricePerStem(2.0).build();
        Flower peony       = Flower.builder().name("Peony")       .colors(List.of("Salmon", "Blush")).pricePerStem(6.5).build();
        Flower lavender    = Flower.builder().name("Lavender")    .colors(List.of("Lavender")).pricePerStem(2.2).build();
        Flower hydrangea   = Flower.builder().name("Hydrangea")   .colors(List.of("Blue", "Cream")).pricePerStem(5.8).build();

        flowerRepo.saveAll(List.of(
                rose, tulip, lily, orchid, sunflower,
                carnation, daisy, peony, lavender, hydrangea));

         romanticSurprise = Bouquet.builder().name("Romantic Surprise").description("Perfect for anniversaries").build();
         springJoy        = Bouquet.builder().name("Spring Joy").description("Colour splash for every day").build();
        Bouquet summerBreeze     = Bouquet.builder().name("Summer Breeze").description("Light and airy").build();
        Bouquet winterWhisper    = Bouquet.builder().name("Winter Whisper").description("Cool whites and blues").build();
        Bouquet sunsetGlow       = Bouquet.builder().name("Sunset Glow").description("Warm evening tones").build();
        Bouquet morningDew       = Bouquet.builder().name("Morning Dew").description("Fresh start bouquets").build();
        Bouquet classicElegance  = Bouquet.builder().name("Classic Elegance").description("Timeless whites").build();
        Bouquet vibrantFantasy   = Bouquet.builder().name("Vibrant Fantasy").description("Bold and bright mix").build();
        Bouquet rusticCharm      = Bouquet.builder().name("Rustic Charm").description("Countryside feel").build();
        Bouquet pastelDream      = Bouquet.builder().name("Pastel Dream").description("Soft pastel palette").build();

        bouquetRepo.saveAll(List.of(
                romanticSurprise, springJoy, summerBreeze, winterWhisper, sunsetGlow,
                morningDew, classicElegance, vibrantFantasy, rusticCharm, pastelDream));

        List<FlowerBouquet> links = List.of(
                FlowerBouquet.builder().bouquet(romanticSurprise).flower(rose).quantity(9).stemLength(30).build(),
                FlowerBouquet.builder().bouquet(romanticSurprise).flower(peony).quantity(5).stemLength(28).build(),
                FlowerBouquet.builder().bouquet(romanticSurprise).flower(carnation).quantity(7).stemLength(29).build(),

                FlowerBouquet.builder().bouquet(springJoy).flower(tulip).quantity(11).stemLength(27).build(),
                FlowerBouquet.builder().bouquet(springJoy).flower(daisy).quantity(10).stemLength(25).build(),
                FlowerBouquet.builder().bouquet(springJoy).flower(lavender).quantity(12).stemLength(24).build(),
                FlowerBouquet.builder().bouquet(springJoy).flower(hydrangea).quantity(3).stemLength(26).build(),

                FlowerBouquet.builder().bouquet(summerBreeze).flower(sunflower).quantity(6).stemLength(35).build(),
                FlowerBouquet.builder().bouquet(summerBreeze).flower(rose).quantity(4).stemLength(32).build(),
                FlowerBouquet.builder().bouquet(summerBreeze).flower(lavender).quantity(8).stemLength(30).build(),


                FlowerBouquet.builder().bouquet(winterWhisper).flower(lily).quantity(6).stemLength(28).build(),
                FlowerBouquet.builder().bouquet(winterWhisper).flower(hydrangea).quantity(4).stemLength(27).build(),
                FlowerBouquet.builder().bouquet(winterWhisper).flower(orchid).quantity(3).stemLength(30).build(),
                FlowerBouquet.builder().bouquet(winterWhisper).flower(rose).quantity(5).stemLength(28).build(),

                FlowerBouquet.builder().bouquet(sunsetGlow).flower(sunflower).quantity(7).stemLength(34).build(),
                FlowerBouquet.builder().bouquet(sunsetGlow).flower(tulip).quantity(5).stemLength(26).build(),
                FlowerBouquet.builder().bouquet(sunsetGlow).flower(peony).quantity(4).stemLength(27).build(),
                FlowerBouquet.builder().bouquet(sunsetGlow).flower(carnation).quantity(6).stemLength(28).build(),
                FlowerBouquet.builder().bouquet(sunsetGlow).flower(rose).quantity(3).stemLength(30).build(),

                FlowerBouquet.builder().bouquet(morningDew).flower(daisy).quantity(10).stemLength(25).build(),
                FlowerBouquet.builder().bouquet(morningDew).flower(lily).quantity(4).stemLength(27).build(),
                FlowerBouquet.builder().bouquet(morningDew).flower(lavender).quantity(8).stemLength(24).build(),


                FlowerBouquet.builder().bouquet(classicElegance).flower(lily).quantity(8).stemLength(28).build(),
                FlowerBouquet.builder().bouquet(classicElegance).flower(rose).quantity(6).stemLength(30).build(),
                FlowerBouquet.builder().bouquet(classicElegance).flower(hydrangea).quantity(3).stemLength(27).build(),


                FlowerBouquet.builder().bouquet(vibrantFantasy).flower(orchid).quantity(5).stemLength(29).build(),
                FlowerBouquet.builder().bouquet(vibrantFantasy).flower(sunflower).quantity(5).stemLength(34).build(),
                FlowerBouquet.builder().bouquet(vibrantFantasy).flower(tulip).quantity(6).stemLength(27).build(),
                FlowerBouquet.builder().bouquet(vibrantFantasy).flower(carnation).quantity(7).stemLength(28).build(),
                FlowerBouquet.builder().bouquet(vibrantFantasy).flower(peony).quantity(5).stemLength(27).build(),


                FlowerBouquet.builder().bouquet(rusticCharm).flower(sunflower).quantity(6).stemLength(35).build(),
                FlowerBouquet.builder().bouquet(rusticCharm).flower(daisy).quantity(12).stemLength(25).build(),
                FlowerBouquet.builder().bouquet(rusticCharm).flower(lavender).quantity(10).stemLength(24).build(),
                FlowerBouquet.builder().bouquet(rusticCharm).flower(carnation).quantity(4).stemLength(28).build(),


                FlowerBouquet.builder().bouquet(pastelDream).flower(peony).quantity(6).stemLength(27).build(),
                FlowerBouquet.builder().bouquet(pastelDream).flower(tulip).quantity(6).stemLength(26).build(),
                FlowerBouquet.builder().bouquet(pastelDream).flower(rose).quantity(5).stemLength(29).build(),
                FlowerBouquet.builder().bouquet(pastelDream).flower(hydrangea).quantity(4).stemLength(27).build()
        );

        fbRepo.saveAll(links);



    }


    private void linkBouquetsToCatalogues() {

        BouquetCatalogue bc1 = BouquetCatalogue.builder()
                .bouquet(romanticSurprise)
                .catalogue(wedding)
                .isEditorsChoice(true)
                .dateAdded(LocalDateTime.now().minusDays(7))
                .build();

        BouquetCatalogue bc2 = BouquetCatalogue.builder()
                .bouquet(springJoy)
                .catalogue(everyday)
                .isEditorsChoice(false)
                .dateAdded(LocalDateTime.now().minusDays(3))
                .build();

        bouquetCatalogueRepo.saveAll(List.of(bc1, bc2));
    }


    private void linkAccessoriesToBouquets() {

        AccessoryBouquet ab1 = AccessoryBouquet.builder()
                .bouquet(romanticSurprise)
                .accessory(ribbon)
                .quantity(1)
                .build();

        AccessoryBouquet ab2 = AccessoryBouquet.builder()
                .bouquet(springJoy)
                .accessory(burlap)
                .quantity(2)
                .build();

        accessoryBouquetRepo.saveAll(List.of(ab1, ab2));
    }


    private void initCustomersOrders() {

        Customer ivan = Customer.builder()
                .name("Ivan Petrenko")
                .email("ivan@example.com")
                .phoneNumber("123456789")
                .loyaltyPoints(9)
                .build();

        Customer ola = Customer.builder()
                .name("Ola Kowalska")
                .email("ola@example.com")
                .phoneNumber("987654321")
                .loyaltyPoints(18)
                .build();


        Order o1 = new Order();
        o1.setCustomer(ivan);
        o1.setSpecialPreference("no ribbon, please");
        o1.setDeliveryAddress("Warsaw Piekna 15");
        o1.setDeliveryDate(LocalDateTime.now().plusDays(1));
        o1.getOrderBouquets().add(
                OrderBouquet.builder().order(o1).bouquet(romanticSurprise).quantity(1).build());

        Payment pay1 = Payment.builder()
                .order(o1)
                .amount(o1.getPrice())
                .method(PaymentMethod.CARD)
                .status(PaymentStatus.COMPLETED)
                .build();
        o1.setPayment(pay1);

        Review rev1 = Review.builder()
                .order(o1)
                .rating(5)
                .comment("Absolutely gorgeous!")
                .date(LocalDateTime.now().minusDays(1))
                .build();
        o1.setReview(rev1);


        Order o2 = new Order();
        o2.setCustomer(ola);
        o2.setDeliveryAddress("Krakow Rynek 1");
        o2.setDeliveryDate(LocalDateTime.now().plusDays(2));
        o2.getOrderBouquets().add(
                OrderBouquet.builder().order(o2).bouquet(springJoy).quantity(2).build());

        Payment pay2 = Payment.builder()
                .order(o2)
                .amount(o2.getPrice())
                .method(PaymentMethod.CASH)
                .status(PaymentStatus.COMPLETED)
                .build();
        o2.setPayment(pay2);

        customerRepo.saveAll(List.of(ivan, ola));


        EmployeeOrder eo1 = EmployeeOrder.builder()
                .employee(florist)
                .order(o1)
                .startedWorkingOn(LocalDateTime.now().minusHours(2))
                .finishedWorkingOn(LocalDateTime.now().minusHours(1))
                .build();

        EmployeeOrder eo2 = EmployeeOrder.builder()
                .employee(student)
                .order(o2)
                .startedWorkingOn(LocalDateTime.now().minusMinutes(30))
                .build();

        customerRepo.saveAll(List.of(ivan, ola));
        orderRepo.saveAll(List.of(o1, o2));
        employeeOrderRepo.saveAll(List.of(eo1, eo2));


    }
}
