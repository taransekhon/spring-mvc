package com.reactivestax.spring5mvc.validators;

import java.util.List;

public interface ValidationRuleFactory {
    public List<ValidationRule> getValidationRulesForBusinessRuleCheck();
}
