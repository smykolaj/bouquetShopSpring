package org.example.finalproject.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.finalproject.models.Review;

import java.util.Optional;
import java.util.Set;

public class ReviewConsistencyValidator
        implements ConstraintValidator<ReviewConsistency, Review> {

    private static final Set<String> POSITIVE =
            Set.of("amazing","perfect","great","wonderful","incredible");

    @Override
    public boolean isValid(Review review, ConstraintValidatorContext ctx) {
        if (review == null) return true;

        String c = Optional.ofNullable(review.getComment())
                .orElse("")
                .toLowerCase();

        boolean containsPositive = POSITIVE.stream()
                .anyMatch(c::contains);

        return !(containsPositive && review.getRating() < 2);
    }
}