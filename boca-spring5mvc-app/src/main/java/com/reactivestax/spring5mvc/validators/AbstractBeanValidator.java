package com.reactivestax.spring5mvc.validators;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * This is the Abstract validator class that all applications can re-use.
 * <p>
 * If an application doesn't have any specific business
 * functionality to be validated, then just having this class
 * will provide the validation functionality out-of-the-box.
 *
 * An app-developer can simply annotate their JSON objects with JSR-303 annotations
 * like @NotBlank etc and this class will take care of validating the objects at runtime
 * and reporting errors using RestErrorHander.
 * <p>
 * If an application does have any specific business functionality to be validated,
 * they can simply write their own BeanValidator that extends this class. And provide their business
 * validation logic in their own implementation for this class' abstract validateBusinessRules method.
 */
public abstract class AbstractBeanValidator implements org.springframework.validation.Validator, InitializingBean {

    private Validator validator;

    @Override
    public void afterPropertiesSet() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }
        validateBusinessRules(target, errors);
    }

    protected abstract void validateBusinessRules(Object target, Errors errors);
}