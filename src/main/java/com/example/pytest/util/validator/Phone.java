package com.example.pytest.util.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {Phone.PhoneValidator.class}
)
@Repeatable(Phone.List.class)
public @interface Phone {

    String regexp() default "^09[0-9]{8}$";

    String message() default "手機格式不符" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    jakarta.validation.constraints.Pattern.Flag[] flags() default {};
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List{
        Phone[] value();
    }

    class PhoneValidator implements ConstraintValidator<Phone,String>{
        private Phone phone;

        @Override
        public void initialize(Phone constraintAnnotation) {
            phone = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            String regexp = phone.regexp();
            return value.matches(regexp);
        }
    }
}
