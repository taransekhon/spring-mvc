package com.reactivestax.spring5mvc.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationRuleFactoryForWidgetController implements ValidationRuleFactory{

    @Autowired
    DescriptionValidationRule descriptionValidationRule;

    @Autowired
    NameValidationRule nameValidationRule;

    private List<ValidationRule> validationRuleList = new ArrayList<>();

    @PostConstruct
    public void setup(){
        validationRuleList.add(descriptionValidationRule);
        validationRuleList.add(nameValidationRule);
    }

    public List<ValidationRule> getValidationRulesForBusinessRuleCheck(){
        return validationRuleList;
    }
}
