package com.reactivestax.spring5mvc.validators;

import com.reactivestax.spring5mvc.model.Widget;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NameValidationRule implements ValidationRule<Widget> {
    @Override
    public void validate(Widget widget, Map<String, String> errorMap){
        if(!StringUtils.startsWith(widget.getName(),"name")){
            errorMap.put("name", "name also has to start with \'name\'");
        }
    }
}
