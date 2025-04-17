package io.github.joaoVitorLeal.validation.constraintvalidation;

import io.github.joaoVitorLeal.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator <NotEmptyList, List> { // <Nome da annotation personalizada, o tipo de objeto que será validado>

    /**
     * Podemos obter algum dado da annotation como mensagem, ou outra propriedade.
     * */
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty(); // validação
    }
}
