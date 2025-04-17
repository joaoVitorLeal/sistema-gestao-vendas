package io.github.joaoVitorLeal.validation;

import io.github.joaoVitorLeal.validation.constraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class) // Classe que implementa a validação desta annotation personalizada
public @interface NotEmptyList {
    // Para criar uma annotation personalizada de validação,
    // é obrigatório ter esses 3 métodos.
    String message() default "A lista não pode ser vazia.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
