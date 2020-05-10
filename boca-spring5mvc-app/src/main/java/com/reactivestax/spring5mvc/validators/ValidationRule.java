package com.reactivestax.spring5mvc.validators;

import java.util.Map;

public interface ValidationRule<T> {
    void validate(T t, Map<String,String> errorMap);
}
