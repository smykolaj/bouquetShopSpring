package org.example.finalproject.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.finalproject.enums.EmployeeRole;
import org.example.finalproject.models.Employee;

public class RoleFieldsValidator
        implements ConstraintValidator<RoleFieldsValid, Employee>
{

    @Override
    public boolean isValid(Employee e,
                           ConstraintValidatorContext ctx)
    {

        if (e == null) return true;
        boolean valid = true;
        ctx.disableDefaultConstraintViolation();

        if (e.getRoles().contains(EmployeeRole.CASHIER)
                && e.getDrawerNumber() == null)
        {
            addViolation(ctx, "drawerNumber",
                    "cashier must have drawer number");
            valid = false;
        }

        if (e.getRoles().contains(EmployeeRole.COURIER)
                && (e.getDrivingLicense() == null
                || e.getDrivingLicense().isBlank()))
        {
            addViolation(ctx, "drivingLicense",
                    "courier must have driving licence");
            valid = false;
        }

        if (e.getRoles().contains(EmployeeRole.FLORIST)
                && (e.getSpecialization() == null
                || e.getSpecialization().isBlank()))
        {
            addViolation(ctx, "specialization",
                    "florist must have specialization");
            valid = false;
        }

        return valid;
    }

    private void addViolation(ConstraintValidatorContext ctx,
                              String field, String msg)
    {
        ctx.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}